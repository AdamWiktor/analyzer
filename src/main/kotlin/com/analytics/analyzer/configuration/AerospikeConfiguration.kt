package com.analytics.analyzer.configuration

import com.aerospike.client.Host
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration

private val logger = KotlinLogging.logger { }

@Configuration
@EnableConfigurationProperties(AerospikeConfigurationProperties::class)
class AerospikeConfiguration : AbstractAerospikeDataConfiguration() {

    @Autowired
    lateinit var aerospikeConfigurationProperties: AerospikeConfigurationProperties

    override fun getHosts(): MutableCollection<Host> = aerospikeConfigurationProperties.hosts.mapTo(
        mutableListOf()
    ) { Host(it, aerospikeConfigurationProperties.port) }

    override fun nameSpace(): String = aerospikeConfigurationProperties.namespace
}
