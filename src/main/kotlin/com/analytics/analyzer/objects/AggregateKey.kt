package com.analytics.analyzer.objects

import com.analytics.analyzer.objects.Action
import kotlinx.serialization.Serializable

// TODO it might not be deterministic
@Serializable
data class AggregateKey(
    val timeSeconds: Long,
    val action: Action,
    val origin: String?,
    val brandId: String?,
    val categoryId: String?
)
