package com.analytics.analyzer.objects

import kotlinx.serialization.Serializable

@Serializable
data class UserTagEvent(
    val timeSeconds: Long,
    val action: Action,
    val origin: String,
    val brandId: String,
    val categoryId: String,
    val price: Int
)
