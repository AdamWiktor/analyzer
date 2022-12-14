package com.analytics.analyzer.controllers

import com.analytics.analyzer.services.ProfilesService
import com.analytics.analyzer.objects.UserTag
import com.analytics.analyzer.services.AggregatesService
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
    lateinit var profilesService: ProfilesService

    @Autowired
    lateinit var aggregatesService: AggregatesService

    @PostMapping(path = ["/user_tags"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun postUserTags(@RequestBody userTag: UserTag) {
//        logger.info { "New user tag: $userTag" }
        aggregatesService.sendUserTag(userTag)
        profilesService.addUserTagToService(userTag)
    }
}
