package com.analytics.analyzer

import org.springframework.data.aerospike.repository.AerospikeRepository


interface AerospikeTagsRepository : AerospikeRepository<UserTag, String>