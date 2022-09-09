package com.analytics.analyzer.objects

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Serializable
data class AggregateResponse(
    val columns: List<String>,
    val rows: List<List<String>>
)

fun createHeader(
    origin: String?,
    brandId: String?,
    categoryId: String?,
    aggregates: List<Aggregate>
): List<String> {
    return sequenceOf(
        "1m_bucket",
        "action",
        if (origin != null) "origin" else null,
        if (brandId != null) "brand_id" else null,
        if (categoryId != null) "category_id" else null,
        aggregates[0].name.lowercase(),
        if (aggregates.size == 2) aggregates[1].name.lowercase() else null
    )
        .filter(Objects::nonNull)
        .map { it!! }
        .toList()
}

fun createRow(
    timeSeconds: Long,
    action: Action,
    origin: String?,
    brandId: String?,
    categoryId: String?,
    aggregates: List<Aggregate>,
    record: AggregateRecord
): List<String> {
    val aggregate1 = if (aggregates[0] == Aggregate.COUNT)
        record.count.toString()
    else if (aggregates[0] == Aggregate.SUM_PRICE)
        record.sumPrice.toString()
    else
        null
    val aggregate2 = if (aggregates.size == 2 && aggregates[1] == Aggregate.COUNT)
        record.count.toString()
    else if (aggregates.size == 2 && aggregates[1] == Aggregate.SUM_PRICE)
        record.sumPrice.toString()
    else
        null
    return sequenceOf(
        formatter.format(Instant.ofEpochSecond(timeSeconds)),
        action.name,
        origin,
        brandId,
        categoryId,
        aggregate1,
        aggregate2
    )
        .filter(Objects::nonNull)
        .map { it!! }
        .toList()
}

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.of("UTC"))