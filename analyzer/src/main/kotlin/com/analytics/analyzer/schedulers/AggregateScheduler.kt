package com.analytics.analyzer.schedulers

import com.analytics.analyzer.objects.AggregateRecord
import com.analytics.analyzer.singletons.AggregatesSingleton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Component
class AggregateScheduler {

    companion object {
        private const val EXPIRATION_SECONDS: Long = 60
    }

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    fun sendAggregatesToAerospike() {
        val bucket = AggregatesSingleton.swapAndGetOldWriteBucket()
        for ((key, value) in bucket) {
            val record = AggregateRecord(key, EXPIRATION_SECONDS, 0, 0)
            aerospikeTemplate.add(record, mapOf(Pair("count", value.count), Pair("sumPrice", value.sumPrice)))
        }
        logger.info { "Scheduler added ${bucket.size} aggregates" }
        bucket.clear()
    }
}
