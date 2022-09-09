package com.analytics.analyzer.configuration

import com.aerospike.client.Host
import com.aerospike.client.policy.ClientPolicy
import com.aerospike.client.policy.CommitLevel
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

    override fun getClientPolicy(): ClientPolicy {
        val clientPolicy = ClientPolicy()
        clientPolicy.failIfNotConnected = true
        clientPolicy.timeout = 35000
        clientPolicy.writePolicyDefault.sendKey = aerospikeDataSettings().isSendKey
        clientPolicy.writePolicyDefault.socketTimeout = 15000
        clientPolicy.writePolicyDefault.totalTimeout = 35000
        clientPolicy.writePolicyDefault.maxRetries = 2
        clientPolicy.writePolicyDefault.commitLevel = CommitLevel.COMMIT_MASTER
        clientPolicy.batchPolicyDefault.maxConcurrentThreads = 5
        return clientPolicy
    }
}
