package com.analytics.analyzer.controllers

import com.analytics.analyzer.objects.Action
import com.analytics.analyzer.objects.Profile
import com.analytics.analyzer.objects.UserTag
import com.analytics.analyzer.services.TagsService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

private val logger = KotlinLogging.logger { }

@RestController
class UserProfilesController {

    @Autowired
    lateinit var tagsService: TagsService

    @PostMapping(path = ["/user_profiles/{cookie}"])
    fun postUserProfiles(
        @PathVariable cookie: String,
        @RequestParam(name = "time_range") timeRange: String,
        @RequestParam(defaultValue = "200") limit: Long
    ): Profile {
        logger.info { "New profile request: $cookie, $timeRange, $limit" }
        val timeRanges = timeRange.split("_")
        val begin = Instant.parse(timeRanges[0] + "Z").toEpochMilli()
        val end = Instant.parse(timeRanges[1] + "Z").toEpochMilli()
        val userTags = tagsService.getTags(cookie)
        val views = filterUserTags(userTags, Action.VIEW, begin, end, limit)
        val buys = filterUserTags(userTags, Action.BUY, begin, end, limit)
        return Profile(cookie, views, buys)
    }

    private fun filterUserTags(
        userTags: List<UserTag>,
        action: Action,
        begin: Long,
        end: Long,
        limit: Long
    ): List<UserTag> {
        return userTags.stream()
            .filter { it.action == action }
            .filter { it.timeMillis >= begin }
            .filter { it.timeMillis < end }
            .sorted(Comparator.comparingLong(UserTag::timeMillis).reversed())
            .limit(limit)
            .toList()
    }
}
