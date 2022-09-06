package com.analytics.analyzer.controllers

import com.analytics.analyzer.services.ProfilesService
import com.fasterxml.jackson.databind.JsonNode
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
class AggregatesController {

    @Autowired
    lateinit var tagsService: ProfilesService

    @PostMapping(path = ["/aggregates"])
    fun postAggregates(@RequestBody node: JsonNode): JsonNode {
        return node
    }
}
