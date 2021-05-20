package com.example.hibernatedemo.hibernatedemo

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.util.*

interface WithProvidedUuidId {
    val migrationId: UUID?
}

class MigrationUuidGenerator : IdentifierGenerator, Configurable {
    private val logger: Logger = LoggerFactory.getLogger(MigrationUuidGenerator::class.java)

    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor, obj: Any): Serializable {
        val withUuidId = obj as WithProvidedUuidId
        if (withUuidId.migrationId != null) {
            logger.info("Using provided id {}", withUuidId.migrationId)
            return withUuidId.migrationId!!
        } else {
            logger.info("Generated new id")
            return UUID.randomUUID()
        }
    }

    @Throws(MappingException::class)
    override fun configure(type: Type?, properties: Properties,
                           serviceRegistry: ServiceRegistry?) {
    }
}