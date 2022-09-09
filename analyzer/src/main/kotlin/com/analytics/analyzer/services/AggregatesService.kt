package com.analytics.analyzer.services

import com.analytics.analyzer.objects.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.data.aerospike.core.model.GroupedKeys
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.security.MessageDigest

private val logger = KotlinLogging.logger {}

@Service
class AggregatesService {

    companion object {
        const val TOPIC_NAME = "aggr"
    }

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    private val md: MessageDigest = MessageDigest.getInstance("SHA3-256")

    fun sendUserTag(userTag: UserTag) {
        kafkaTemplate.send(TOPIC_NAME, Json.encodeToString(userTag.toEvent()))
    }

    fun getAggregates(
        begin: Long,
        end: Long,
        action: Action,
        origin: String?,
        brandId: String?,
        categoryId: String?
    ): List<Pair<AggregateRecord, Long>> {
        val keys: List<String> = ((begin / 60) until (end / 60)).toList()
            .map { AggregateKey(it * 60, action, origin, brandId, categoryId).uniqueHash(md) }
        val records = aerospikeTemplate
            .findByIds(keys, AggregateRecord::class.java).associateBy { it.id }
        return keys.asSequence()
            .zip(((begin / 60) until (end / 60)).asSequence())
            .map { records.getOrDefault(it.first, AggregateRecord(it.first, 0, 0)) to it.second }
            .toList()
    }
}
