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

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    private var index: Int = 0

    private var currentDataBucketBeginTime: Long = 0

    @Scheduled(cron = "15,30,45 * * * * *") // at second 15 and 45
    fun sendAggregatesToAerospike() {
        logger.info { "Scheduler triggered, curr=$index, size0=${AggregatesSingleton.buckets[0].size}, size1=${AggregatesSingleton.buckets[1].size}, size2=${AggregatesSingleton.buckets[2].size}" }
        if (!AggregatesSingleton.processingBegan)
            return
        for ((key, value) in AggregatesSingleton.buckets[index]) {
            val record = AggregateRecord(Json.encodeToString(key), 0, 0)
            // TODO expiration/ttl=25h
            aerospikeTemplate.add(record, mapOf(Pair("count", value.count), Pair("sumPrice", value.sumPrice)))
        }
        logger.info { "Scheduler added ${AggregatesSingleton.buckets[index].size} aggregates" }
        AggregatesSingleton.buckets[index].clear()
        index = AggregatesSingleton.nextIndex(index)
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    fun bbb() {
        if (!AggregatesSingleton.processingBegan)
            return
        if (currentDataBucketBeginTime == 0L)
            currentDataBucketBeginTime = AggregatesSingleton.firstDataBucketBeginTime
        if (AggregatesSingleton.timestampSecondsMax < currentDataBucketBeginTime + 45)
            return
        for ((key, value) in AggregatesSingleton.buckets[index]) {
            val record = AggregateRecord(Json.encodeToString(key), 0, 0)
            // TODO expiration/ttl=25h
            aerospikeTemplate.add(record, mapOf(Pair("count", value.count), Pair("sumPrice", value.sumPrice)))
        }
        logger.info { "Scheduler added ${AggregatesSingleton.buckets[index].size} aggregates" }
        AggregatesSingleton.buckets[index].clear()
        index = AggregatesSingleton.nextIndex(index)
        currentDataBucketBeginTime += 30
    }
}
// TODO
/**
 * dodajemy eventy do kubełków i trzymamy timestampMax. jak timestampMax będzie 15s ponad koniec kubełka, to można wysyłać
 */

/**
 *
 */