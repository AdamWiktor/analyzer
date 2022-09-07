package com.analytics.analyzer.controllers

import com.analytics.analyzer.objects.*
import com.analytics.analyzer.services.AggregatesService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

private val logger = KotlinLogging.logger { }

@RestController
class AggregatesController {

    @Autowired
    lateinit var aggregatesService: AggregatesService

    @PostMapping(path = ["/aggregates"])
    fun postAggregates(
        @RequestParam(name = "time_range") timeRange: String,
        @RequestParam action: Action,
        @RequestParam aggregates: List<Aggregate>,
        @RequestParam(required = false) origin: String?,
        @RequestParam(value = "brand_id", required = false) brandId: String?,
        @RequestParam(value = "category_id", required = false) categoryId: String?
    ): AggregateResponse {
        val timeRanges = timeRange.split("_")
        val begin = Instant.parse(timeRanges[0] + "Z").toEpochMilli()
        val end = Instant.parse(timeRanges[1] + "Z").toEpochMilli()
        val records = aggregatesService.getAggregates(begin, end, action, origin, brandId, categoryId)
        return AggregateResponse(
            createHeader(origin, brandId, categoryId, aggregates),
            records.map { createRow(origin, brandId, categoryId, aggregates, it) }
        )
    }
}
