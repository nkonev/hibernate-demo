package com.example.hibernatedemo.hibernatedemo

import org.hibernate.dialect.PostgreSQL10Dialect
import java.sql.Types
import org.hibernate.type.LongType

class FixedPostgresDialect : PostgreSQL10Dialect() {
    init {
        registerHibernateType(Types.BIGINT, LongType.INSTANCE.name)
        registerHibernateType(Types.OTHER, "pg-uuid")
    }
}
