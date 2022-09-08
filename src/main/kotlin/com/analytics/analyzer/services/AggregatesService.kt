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
import org.springframework.web.bind.annotation.RequestParam

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
    ): List<AggregateRecord> {
        val keys: List<String> = ((begin / 60) until  (end / 60))
            .map { Json.encodeToString(AggregateKey(it * 60, action, origin, brandId, categoryId)) }
        // TODO batch read policy
        val entities =
            aerospikeTemplate.findByIds(GroupedKeys.builder().entityKeys(AggregateRecord::class.java, keys).build())
        val records = entities.getEntitiesByClass(AggregateRecord::class.java).associateBy { it.id }
        return keys.map { records[it] ?: AggregateRecord(it, 0, 0) }
    }
}
