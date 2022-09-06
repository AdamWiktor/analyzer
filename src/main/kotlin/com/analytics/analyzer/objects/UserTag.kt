package com.analytics.analyzer.objects

import com.aerospike.client.query.IndexType
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.aerospike.annotation.Indexed
import org.springframework.data.aerospike.mapping.Document

@Document(collection = "tags")
data class UserTag(
    val time: String,
    @Indexed(type = IndexType.STRING) val cookie: String,
    val country: String,
    val device: Device,
    val action: Action,
    val origin: String,
    @JsonProperty("product_info") val productInfo: ProductInfo,
//    @Id @JsonIgnore val id: String = UUID.randomUUID().toString()
)
