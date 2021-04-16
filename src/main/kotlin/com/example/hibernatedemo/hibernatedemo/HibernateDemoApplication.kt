package com.example.hibernatedemo.hibernatedemo

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.function.Consumer
import javax.persistence.*

@EntityScan("com.example.hibernatedemo.hibernatedemo")
@SpringBootApplication
@EnableJpaRepositories("com.example.hibernatedemo.hibernatedemo")
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

interface PersonRepository: JpaRepository<Person, UUID>

interface ApplicationRepository: JpaRepository<Application, UUID>

@Entity
@Table(name = "person")
data class Person(
	@Id
	val id: UUID,
	var firstName: String,
	var secondName: String
)

@Entity
@Table(name = "application")
data class Application(
	@Id
	val id: UUID,
	var name: String,
	@OneToOne
	@JoinColumn(name="main_person_id")
	var mainPerson: Person,
	@OneToMany(fetch = FetchType.EAGER) // or @Transactional for lazy
	@JoinTable(
			name="application_person",
			joinColumns = [JoinColumn(name="application_id")],
			inverseJoinColumns = [JoinColumn(name="person_id")]
	)
	var secondaryPersons: Collection<Person>
)