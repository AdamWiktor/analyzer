package com.analytics.analyzer.listeners

import com.analytics.analyzer.objects.AggregateKey
import com.analytics.analyzer.objects.UserTagEvent
import com.analytics.analyzer.singletons.AggregatesSingleton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.security.MessageDigest

private val logger = KotlinLogging.logger {}

@Component
@KafkaListener(topics = ["aggr"])
class AggregateListener {

    private val md: MessageDigest = MessageDigest.getInstance("SHA3-256")

    @KafkaHandler
    fun handleUserTagEvent(message: String) {
        val event = Json.decodeFromString<UserTagEvent>(message)
        val origins = arrayListOf(event.origin, null)
        val brandIds = arrayListOf(event.brandId, null)
        val categoryIds = arrayListOf(event.categoryId, null)
        for (origin in origins) {
            for (brandId in brandIds) {
                for (categoryId in categoryIds) {
                    val key = AggregateKey((event.timeSeconds / 60) * 60, event.action, origin, brandId, categoryId)
                        .uniqueHash(md)
                    AggregatesSingleton.putData(key, event.price)
                }
            }
        }
    }
}
