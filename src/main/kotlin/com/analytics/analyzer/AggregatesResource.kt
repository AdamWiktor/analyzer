package com.analytics.analyzer

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregatesResource {

    @PostMapping(path = ["/aggregates"])
    fun postAggregates(@RequestBody node: JsonNode): JsonNode {
        return node
    }
}
