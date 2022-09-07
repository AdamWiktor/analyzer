package com.analytics.analyzer.listeners

import com.analytics.analyzer.objects.UserTagEvent
import com.analytics.analyzer.objects.AggregateKey
import com.analytics.analyzer.objects.AggregateValue
import com.analytics.analyzer.singletons.AggregatesArray
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.Instant

private val logger = KotlinLogging.logger {}

@Component
@KafkaListener(topics = ["aggr"])
class AggregateListener {

    companion object {
        const val TIME_WINDOW_DURATION_SECONDS = 30
        fun floor(timestamp: Long) = (timestamp / TIME_WINDOW_DURATION_SECONDS) * TIME_WINDOW_DURATION_SECONDS
    }

    private var currentWrite: Int = 0
    private var currentWriteTimeWindow: Long = 0

    @KafkaHandler
    fun handleUserTagEvent(message: String) {
        if (!AggregatesArray.processingBegan)
            beginProcessing()
        else
            maybeAdvanceTimeWindow()
        val event = Json.decodeFromString<UserTagEvent>(message)
        val timeDiff = event.timeSeconds - currentWriteTimeWindow
        val write = if (timeDiff < 0) {
            logger.info { "New user tag event $event dropped" }
            return
        } else if (timeDiff < TIME_WINDOW_DURATION_SECONDS) {
            currentWrite
        } else {
            AggregatesArray.nextIndex(currentWrite)
        }
        val origins = arrayListOf(event.origin, null)
        val brandIds = arrayListOf(event.brandId, null)
        val categoryIds = arrayListOf(event.categoryId, null)
        for (origin in origins) {
            for (brandId in brandIds) {
                for (categoryId in categoryIds) {
                    val key = AggregateKey((event.timeSeconds / 60) * 60, event.action, origin, brandId, categoryId)
                    val value = AggregatesArray.aggregates[write][key] ?: AggregateValue(0, 0)
                    value.count++
                    value.sumPrice += event.price
                    AggregatesArray.aggregates[currentWrite][key] = value
                }
            }
        }
    }

    private fun beginProcessing() {
        currentWriteTimeWindow = floor(Instant.now().epochSecond) - TIME_WINDOW_DURATION_SECONDS
        AggregatesArray.processingBegan = true
    }

    private fun maybeAdvanceTimeWindow() {
        val newWriteTime = floor(Instant.now().epochSecond) - TIME_WINDOW_DURATION_SECONDS
        if (newWriteTime == currentWriteTimeWindow)
            return
        val currentWriteDiff = ((newWriteTime - currentWriteTimeWindow) / TIME_WINDOW_DURATION_SECONDS).toInt()
        currentWriteTimeWindow = newWriteTime
        currentWrite = AggregatesArray.advanceIndex(currentWrite, currentWriteDiff)
    }
}