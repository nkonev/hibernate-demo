package com.example.hibernatedemo.hibernatedemo

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.function.Consumer

@EntityScan("com.example.hibernatedemo.hibernatedemo")
@SpringBootApplication
@EnableJdbcRepositories("com.example.hibernatedemo.hibernatedemo")
class HibernateDemoApplication

fun main(args: Array<String>) {
	runApplication<HibernateDemoApplication>(*args)
}

@Component
class Printer(
		private val personRepository: PersonRepository,
		private val applicationRepository: ApplicationRepository
): CommandLineRunner {
	private val logger = LoggerFactory.getLogger(this::class.java)

	// @Transactional
	override fun run(vararg args: String?) {
		logger.info("===== Printing persons =====")
		personRepository.findAll().forEach(Consumer {
			logger.info("{}", it)
		})
		logger.info("===== End of printing persons =====")

		logger.info("===== Printing applications =====")
		applicationRepository.findAll().forEach(Consumer {
			logger.info("{}", it)
		})
		logger.info("=====End of printing applications =====")
	}
}

interface PersonRepository: CrudRepository<Person, UUID>

interface ApplicationRepository: CrudRepository<Application, UUID>

@Table("person")
data class Person(
	@Id
	val id: UUID,
	var firstName: String,
	var secondName: String
)

@Table("main_person")
data class MainPerson(
	@Id
	val id: UUID,
	var firstName: String,
	var secondName: String
)

@Table("secondary_person")
data class SecondaryPerson(
	@Id
	val id: UUID,
	var firstName: String,
	var secondName: String
)

@Table("application")
data class Application(
	@Id
	val id: UUID,
	var name: String,
	@Column("application_id")
	var mainPerson: MainPerson,
	@MappedCollection(idColumn = "application_id", keyColumn = "id")
	var secondaryPersons: Collection<SecondaryPerson>
)