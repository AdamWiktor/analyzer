package com.analytics.analyzer.repositories

import com.analytics.analyzer.objects.UserTag
import org.springframework.data.aerospike.repository.AerospikeRepository


interface AerospikeTagsRepository : AerospikeRepository<UserTag, String>