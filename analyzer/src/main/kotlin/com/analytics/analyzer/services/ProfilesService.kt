package com.analytics.analyzer.services

import com.aerospike.client.AerospikeException
import com.aerospike.client.ResultCode
import com.aerospike.client.policy.CommitLevel
import com.aerospike.client.policy.GenerationPolicy
import com.aerospike.client.policy.WritePolicy
import com.analytics.analyzer.objects.Action
import com.analytics.analyzer.objects.Profile
import com.analytics.analyzer.objects.ProfileRecord
import com.analytics.analyzer.objects.UserTag
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.RecoverableDataAccessException
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ProfilesService {

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    fun getProfile(cookie: String): Profile {
        val record: ProfileRecord = aerospikeTemplate.findById(cookie, ProfileRecord::class.java)
            ?: throw NotFoundException()
        return record.toProfile()
    }

    fun addUserTagToService(userTag: UserTag) {
        for (i in 1..3) {
            val profile = aerospikeTemplate.findById(userTag.cookie, ProfileRecord::class.java)?.toProfile()
                ?: Profile(userTag.cookie, 0, emptyList(), emptyList())
            val views: List<UserTag> =
                if (userTag.action == Action.VIEW)
                    addUserTag(profile.views, userTag)
                else
                    profile.views
            val buys: List<UserTag> =
                if (userTag.action == Action.BUY)
                    addUserTag(profile.buys, userTag)
                else
                    profile.buys
            val newProfile = Profile(profile.cookie, profile.generation, views, buys)
            val writePolicy = WritePolicy()
            writePolicy.socketTimeout = 15000
            writePolicy.totalTimeout = 35000
            writePolicy.maxRetries = 2
            writePolicy.commitLevel = CommitLevel.COMMIT_MASTER
            writePolicy.generation = newProfile.generation
            writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL
            try {
                aerospikeTemplate.persist(newProfile.toRecord(), writePolicy)
                return
            } catch (e: RecoverableDataAccessException) {
                val cause = e.cause
                if (cause is AerospikeException && cause.resultCode == ResultCode.GENERATION_ERROR)
                    logger.info { "Generation error while trying to update the profile for cookie: ${userTag.cookie}, attempt: $i - retrying" }
                else
                    throw e
            }
        }
    }

    private fun addUserTag(userTags: List<UserTag>, userTag: UserTag): List<UserTag> {
        return (userTags.asSequence() + sequenceOf(userTag))
            .sortedWith(Comparator.comparingLong(UserTag::timeMillis).reversed())
            .take(250)
            .toList()
    }
}
