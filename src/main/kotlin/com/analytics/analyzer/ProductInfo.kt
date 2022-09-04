package com.analytics.analyzer

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductInfo(
    @JsonProperty("product_id") val productId: String,
    @JsonProperty("brand_id") val brandId: String,
    @JsonProperty("category_id") val categoryId: String,
    val price: Int
)
