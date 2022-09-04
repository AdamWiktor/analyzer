package com.analytics.analyzer

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class UserTagsResource {

    @PostMapping(path = ["/user_tags"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun postUserTags(@RequestBody userTag: UserTag) {
        logger.info { "New user tag: $userTag" }

        return
    }
}
