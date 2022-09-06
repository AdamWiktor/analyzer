package com.analytics.analyzer.objects

data class Profile(
    val cookie: String,
    val views: List<UserTag>,
    val buys: List<UserTag>
)
