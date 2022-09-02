package com.analytics.analyzer

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProfilesResource {

    @PostMapping(path = ["/user_profiles/{asd}"])
    fun postUserProfiles(@RequestBody node: JsonNode, @PathVariable asd: String): JsonNode {
        return node
    }
}
