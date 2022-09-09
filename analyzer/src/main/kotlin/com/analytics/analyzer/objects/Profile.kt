package com.analytics.analyzer.objects

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.xerial.snappy.Snappy

@Serializable
data class Profile(
    val cookie: String,
    @Transient val generation: Int = 0,
    val views: List<UserTag>,
    val buys: List<UserTag>
) {
    fun toRecord(): ProfileRecord {
        return ProfileRecord(
            cookie,
            generation,
            Snappy.compress(Json.encodeToString(this))
        )
    }
}
