package com.analytics.analyzer.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "kafka")
data class KafkaConfigurationProperties(
    val bootstrapAddress: String,
    val groupId: String
)
