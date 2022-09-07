package com.analytics.analyzer.schedulers

import com.analytics.analyzer.objects.AggregateRecord
import com.analytics.analyzer.singletons.AggregatesArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class AggregateScheduler {

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    private var currentRead: Int = 0

    @Scheduled(cron = "15,45 * * * * *") // at second 15 and 45
    fun sendAggregatesToAerospike() {
        logger.info { "Scheduler triggered, curr=$currentRead, size0=${AggregatesArray.aggregates[0].size}, size1=${AggregatesArray.aggregates[1].size}, size2=${AggregatesArray.aggregates[2].size}" }
        if (!AggregatesArray.processingBegan)
            return
        for ((key, value) in AggregatesArray.aggregates[currentRead]) {
            val record = AggregateRecord(Json.encodeToString(key), 0, 0)
            // TODO expiration/ttl=25h
            aerospikeTemplate.add(record, mapOf(Pair("count", value.count), Pair("sumPrice", value.sumPrice)))
        }
        logger.info { "Scheduler added ${AggregatesArray.aggregates[currentRead].size} aggregates" }
        AggregatesArray.aggregates[currentRead].clear()
        currentRead = AggregatesArray.nextIndex(currentRead)
    }
}
// TODO
/**
 * dodajemy eventy do kubełków i trzymamy timestampMax. jak timestampMax będzie 15s ponad koniec kubełka, to można wysyłać
 */
