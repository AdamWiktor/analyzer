package com.analytics.analyzer

import com.aerospike.client.query.IndexType
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.tomcat.util.security.MD5Encoder.encode
import org.springframework.data.aerospike.annotation.Indexed
import org.springframework.data.aerospike.mapping.Document
import org.springframework.data.annotation.Id
import java.security.MessageDigest
import java.util.UUID

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
