package com.analytics.analyzer.objects

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.data.aerospike.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.xerial.snappy.Snappy

@Document(collection = "profiles")
data class ProfileRecord(
    @Id val cookie: String,
    @Version val generation: Int,
    val data: ByteArray
) {
    fun toProfile(): Profile {
        val decodedData: Profile = Json.decodeFromString(Snappy.uncompressString(data))
        return Profile(cookie, generation, decodedData.views, decodedData.buys)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileRecord

        if (cookie != other.cookie) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cookie.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
