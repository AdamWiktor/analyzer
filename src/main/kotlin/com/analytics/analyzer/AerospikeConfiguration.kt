package com.analytics.analyzer

import com.aerospike.client.Host
import com.aerospike.client.lua.LuaAerospikeLib.log
import com.aerospike.client.query.IndexType
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.aerospike.IndexAlreadyExistsException
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration
import org.springframework.data.aerospike.core.AerospikeTemplate
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories

private val logger = KotlinLogging.logger { }

@Configuration
@EnableConfigurationProperties(AerospikeConfigurationProperties::class)
@EnableAerospikeRepositories(basePackageClasses = [AerospikeTagsRepository::class])
class AerospikeConfiguration : AbstractAerospikeDataConfiguration() {

    @Autowired
    lateinit var aerospikeConfigurationProperties: AerospikeConfigurationProperties

    override fun getHosts(): MutableCollection<Host> = aerospikeConfigurationProperties.hosts.mapTo(
        mutableListOf()
    ) { Host(it, aerospikeConfigurationProperties.port) }

    override fun nameSpace(): String = aerospikeConfigurationProperties.namespace

    @Bean
    fun createAerospikeIndex(aerospikeTemplate: AerospikeTemplate): Boolean {
        try {
            aerospikeTemplate.createIndex(UserTag::class.java, "tag-cookie-index", "cookie", IndexType.STRING)
            logger.info { "Index tag-cookie-index was successfully created" }
        } catch (e: IndexAlreadyExistsException) {
            logger.info { "Index tag-cookie-index already exists, skipped creating" }
        }
        return true
    }
}
