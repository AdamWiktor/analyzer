package com.analytics.analyzer

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserTagsResource {

    @PostMapping(path = ["/user_tags"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun postUserTags() {
        return
    }
}
