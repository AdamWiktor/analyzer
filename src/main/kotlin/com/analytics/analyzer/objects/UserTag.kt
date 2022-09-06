package com.analytics.analyzer.objects

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UserTag(
    val time: String,
    val cookie: String,
    val country: String,
    val device: Device,
    val action: Action,
    val origin: String,
    @SerialName("product_info") val productInfo: ProductInfo,
    @JsonIgnore val timeMillis: Long = Instant.parse(time).toEpochMilli()
)
