package com.analytics.analyzer.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "aerospike")
data class AerospikeConfigurationProperties(
    val hosts: List<String>,
    val port: Int,
    val namespace: String
)
