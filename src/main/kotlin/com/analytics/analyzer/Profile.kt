package com.analytics.analyzer

data class Profile(
    val cookie: String,
    val views: List<UserTag>,
    val buys: List<UserTag>
)
