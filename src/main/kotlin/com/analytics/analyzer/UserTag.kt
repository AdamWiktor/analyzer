package com.analytics.analyzer

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.tomcat.util.security.MD5Encoder.encode
import java.security.MessageDigest

data class UserTag(
    val time: String,
    val cookie: String,
    val country: String,
    val device: Device,
    val action: Action,
    val origin: String,
    @JsonProperty("product_info") val productInfo: ProductInfo
)

private val MD5: MessageDigest = MessageDigest.getInstance("md5")

fun getHash(userTag: UserTag): String = encode(MD5.digest(userTag.cookie.toByteArray())) + "_" + userTag.cookie
fun getHash(string: String): String = encode(MD5.digest(string.toByteArray()))
