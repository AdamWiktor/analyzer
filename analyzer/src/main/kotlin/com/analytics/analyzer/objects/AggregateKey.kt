package com.analytics.analyzer.objects

import java.security.MessageDigest

data class AggregateKey(
    val timeSeconds: Long,
    val action: Action,
    val origin: String?,
    val brandId: String?,
    val categoryId: String?
) {
    fun uniqueHash(md: MessageDigest): String {
        md.update(timeSeconds.toString().toByteArray())
        md.update(SPACING_BYTE_ARRAY)
        md.update(action.name.toByteArray())
        md.update(SPACING_BYTE_ARRAY)
        md.update(origin?.toByteArray() ?: EMPTY_BYTE_ARRAY)
        md.update(SPACING_BYTE_ARRAY)
        md.update(brandId?.toByteArray() ?: EMPTY_BYTE_ARRAY)
        md.update(SPACING_BYTE_ARRAY)
        md.update(categoryId?.toByteArray() ?: EMPTY_BYTE_ARRAY)
        return String(md.digest())
    }
}

private val EMPTY_BYTE_ARRAY = ByteArray(0)
private val SPACING_BYTE_ARRAY = ":".toByteArray()
