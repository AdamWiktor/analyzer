package com.analytics.analyzer.services

import com.aerospike.client.Value
import com.analytics.analyzer.repositories.AerospikeTagsRepository
import com.analytics.analyzer.objects.UserTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.data.aerospike.query.Qualifier
import org.springframework.data.aerospike.repository.query.AerospikeCriteria
import org.springframework.data.aerospike.repository.query.Query
import org.springframework.stereotype.Service
import kotlin.streams.asSequence

@Service
class TagsService {

    @Autowired
    lateinit var aerospikeTagsRepository: AerospikeTagsRepository

    @Autowired
    lateinit var aerospikeTemplate: AerospikeTemplate

    fun getTags(cookie: String): List<UserTag> {
        return aerospikeTemplate.find(
            Query(
                AerospikeCriteria(
                    "cookie",
                    Qualifier.FilterOperation.EQ,
                    false,
                    Value.get(cookie)
                )
            ), UserTag::class.java
        ).asSequence().toList()
    }

    fun addUserTag(tag: UserTag) {
        aerospikeTagsRepository.save(tag)
    }
}

