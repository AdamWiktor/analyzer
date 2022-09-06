package com.analytics.analyzer.controllers

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregatesController {

    @PostMapping(path = ["/aggregates"])
    fun postAggregates(@RequestBody node: JsonNode): JsonNode {
        return node
    }
}
