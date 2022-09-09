package com.analytics.analyzer.objects

import org.springframework.data.aerospike.annotation.Expiration
import org.springframework.data.aerospike.mapping.Document
import org.springframework.data.annotation.Id

@Document(collection = "aggregates")
data class AggregateRecord(
    @Id val id: String,
    @Expiration val expiration: Long,
    val count: Long,
    val sumPrice: Long
)
