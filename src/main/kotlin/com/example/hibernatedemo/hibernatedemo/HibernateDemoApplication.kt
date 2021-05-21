package com.example.hibernatedemo.hibernatedemo

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
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

	@Transactional
	override fun run(vararg args: String?) {
//		logger.info("===== Printing persons =====")
//		personRepository.findAll().forEach(Consumer {
//			logger.info("{}", it)
//		})
//		logger.info("===== End of printing persons =====")
//
//		logger.info("===== Printing applications =====")
//		applicationRepository.findAll().forEach(Consumer {
//			logger.info("{}", it)
//		})
//		logger.info("=====End of printing applications =====")


//		var m = (Person(firstName = "first main", secondName = "second main"))
//		var s = (Person(firstName = "first secondary", secondName = "second secondary"))
//		var app = Application(name = "olol", mainPerson = m, secondaryPersons = mutableListOf(s))
//		var app2 = applicationRepository.saveAndFlush(app)
//
//		logger.info("=====End of storing applications =====")

//		logger.info("===== Printing applications =====")
//		applicationRepository.findAll().forEach(Consumer {
//			logger.info("{}", it)
//		})
//		val findById = applicationRepository.findById(UUID.fromString("4120d4f6-f6d6-4444-917e-278d46250433"))
//////		logger.info("app: {}", findById.get())
//		val get = findById.get()
//		logger.info("app name: {}", get.name)
//		logger.info("main person: {}", findById.get().mainPerson)
//		logger.info("secondary persons: {}", findById.get().secondaryPersons)
//		applicationRepository.deleteById(UUID.fromString("dd839788-1415-4cf3-bc63-4abf1f8ec36b"))
		val get = Application(
				migrationId= UUID.fromString("aaef8bd9-10c1-47de-937f-b515d35b1537"),
				name = "superApp11122",
				mainPerson = Person(migrationId=UUID.fromString("bbef8bd9-10c1-47de-937f-b515d35b1537"), firstName = "first111222", secondName = "second"),
				secondaryPersons = mutableListOf(Person(migrationId=UUID.fromString("ccef8bd9-10c1-47de-937f-b515d35b1537"), firstName = "two secondary third111222", secondName = "two second secondary third"))
		)
		//newPerson = personRepository.save(newPerson)

		applicationRepository.save(get)
	}
}

interface PersonRepository: JpaRepository<Person, UUID>

interface ApplicationRepository: JpaRepository<Application, UUID>

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "person")
data class Person(
	@GeneratedValue(generator = "migration-generator")
	@Id
	val id: UUID? = null,
	var firstName: String,
	var secondName: String,
	@Transient override var migrationId: UUID? = null
) : WithProvidedUuidId

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "application")
data class Application(
	@GeneratedValue(generator = "migration-generator")
	@Id
	val id: UUID? = null,
	var name: String,
	@OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
	@JoinColumn(name="main_person_id")
	var mainPerson: Person,
	@OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
	@JoinTable(
			name="application_person",
			joinColumns = [JoinColumn(name="application_id")],
			inverseJoinColumns = [JoinColumn(name="person_id")]
	)
	var secondaryPersons: MutableList<Person>,
	@Transient override var migrationId: UUID? = null
): WithProvidedUuidId