package springData;

import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat
import static org.hamcrest.MatcherAssert.*;

import spock.lang.Specification;
import springData.domain.Organizer
import springData.domain.Todo
import springData.domain.OrganizerUser
import springData.repository.OrganizerRepository
import springData.repository.TodoRepository
import springData.repository.UserRepository

@SpringBootTest(classes=[OrganizerApp.class,DbConfig.class])
public class DataSpec extends Specification {
	
	@Autowired
	TodoRepository todoRepo;
	
	@Autowired
	OrganizerRepository organizerRepo;
	
	@Autowired
	UserRepository userRepo;
	
	
	def "user repo empty" () {
		given:
			userRepo.deleteAll()
		expect:
			assertThat(userRepo.findAll(), Matchers.hasSize(0));
	}
	
	def "user store" () {
		given:
			userRepo.deleteAll()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			userRepo.save(u)
		expect:
			assertThat(userRepo.findAll(), Matchers.hasSize(1));
	}
	
	def "user fail password is null" () {
		given:
			userRepo.deleteAll()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword(null)
		when:
			userRepo.save(u)
		then:
			thrown(DataIntegrityViolationException)
	}
	def "user fail login not unique" () {
		given:
			userRepo.deleteAll()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("p1")
			userRepo.save(u)
			u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("p435435")
		when:
			userRepo.save(u)
		then:
			thrown(DataIntegrityViolationException)
	}

	def "organizer store" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			organizerRepo.save(o)
		expect:
			assertThat(organizerRepo.findAll(), Matchers.hasSize(1));
	}
	def "organizer fail no user" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
		when:
			organizerRepo.save(o)
		then:
			thrown(DataIntegrityViolationException)
	}
	
	def "organizer user cascade user" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
		when:
			organizerRepo.save(o)
		then:
			assertThat(userRepo.findByLogin("asd"), equalTo(u))
	}
	
	def "organizer user cascade user deletion" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			organizerRepo.save(o)
		when:
			userRepo.delete(u)
		then:
			assertThat(organizerRepo.findAll(), Matchers.hasSize(0));
	}

	def "organizer user stays for organizer deletion" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			organizerRepo.save(o)
		when:
			organizerRepo.delete(o)
		then:
			assertThat(userRepo.findAll(), Matchers.hasSize(1));
	}
	
	def "two user same organizer" () {
		given:
			userRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			u.getOrganizers().add(o);
			o.setOwner(u)
			userRepo.save(u)
			u = new OrganizerUser();
			u.setLogin("2134")
			u.setPassword("3243")
			u.getOrganizers().add(o);
			o.setOwner(u)
		when:
			userRepo.save(u)
		then:
			thrown(InvalidDataAccessApiUsageException)
	}

	def "organizer user cascade persist todo" () {
		given:
			userRepo.deleteAll()
			todoRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			Todo t = new Todo()
			t.setTask("amsdl")
		when:
			organizerRepo.save(o)
		then:
			assertThat(todoRepo.findByTask("amsdl"), equalTo(t))
	}
	
	def "organizer user cascade delete todo" () {
		given:
			userRepo.deleteAll()
			todoRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			Todo t = new Todo()
			t.setTask("amsdl")
			organizerRepo.save(o)
		when:
			organizerRepo.delete(o);
		then:
			assertThat(organizerRepo.findAll(), Matchers.empty());
	}
	
	def "organizer user deleting ToDo" () {
		given:
			userRepo.deleteAll()
			todoRepo.deleteAll()
			organizerRepo.deleteAll()
			Organizer o = new Organizer()
			OrganizerUser u = new OrganizerUser();
			u.setLogin("asd")
			u.setPassword("asd")
			o.setOwner(u)
			Todo t = new Todo()
			t.setTask("amsdl")
			organizerRepo.save(o)
		when:
			todoRepo.delete(t);
		then:
			assertThat(todoRepo.findAll(), Matchers.empty());
		and:
			assertThat(organizerRepo.findByOwner(u), Matchers.hasSize(1));
	}

	
	def "todo store" () {
		given:
			todoRepo.deleteAll()
			Todo t = new Todo()
			t.setTask("important task")
			todoRepo.save(t)
		expect:
			assertThat(organizerRepo.findAll(), Matchers.hasSize(1));
	}

	
	def "todo fail task is null" () {
		given:
			todoRepo.deleteAll()
			Todo t = new Todo()
		when:
			todoRepo.save(t)
		then:
			thrown(DataIntegrityViolationException)
	}
}
