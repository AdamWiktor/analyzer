package com.analytics.analyzer.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductInfo(
    @SerialName("product_id") val productId: Int,
    @SerialName("brand_id") val brandId: String,
    @SerialName("category_id") val categoryId: String,
    val price: Int
)
