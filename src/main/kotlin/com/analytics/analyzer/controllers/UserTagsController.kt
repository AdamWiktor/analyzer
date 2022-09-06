package com.analytics.analyzer.controllers

import com.analytics.analyzer.services.TagsService
import com.analytics.analyzer.objects.UserTag
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class UserTagsController {

    @Autowired
    lateinit var tagsService: TagsService

    @PostMapping(path = ["/user_tags"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun postUserTags(@RequestBody userTag: UserTag) {
        logger.info { "New user tag: $userTag" }
        tagsService.addUserTag(userTag)
    }
}
