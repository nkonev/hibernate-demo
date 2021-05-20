package com.example.hibernatedemo.hibernatedemo

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.io.Serializable
import java.util.*

interface WithUuidId {
    val id: UUID?
}

class MyGenerator : IdentifierGenerator, Configurable {
    private var prefix: String? = null

    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor, obj: Any): Serializable {
        val withUuidId = obj as WithUuidId
        return withUuidId.id!!
    }

    @Throws(MappingException::class)
    override fun configure(type: Type?, properties: Properties,
                           serviceRegistry: ServiceRegistry?) {
        prefix = properties.getProperty("prefix")
    }
}