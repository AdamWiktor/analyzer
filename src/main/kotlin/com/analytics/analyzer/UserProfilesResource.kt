package com.analytics.analyzer

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
class UserProfilesResource {

    @Autowired
    lateinit var tagsService: TagsService

    @PostMapping(path = ["/user_profiles/{cookie}"])
    fun postUserProfiles(
        @PathVariable cookie: String,
        @RequestParam(name = "time_range") timeRange: String,
        @RequestParam(defaultValue = "200") limit: Int
    ): Profile {
        logger.info { "New profile request: $cookie, $timeRange, $limit" }
//        return Profile(cookie, emptyList(), emptyList())
        val a = tagsService.getTags(cookie)
        return Profile(cookie, a, emptyList())
    }
}
