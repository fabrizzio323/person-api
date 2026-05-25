package com.fabrizio.udemy.repository;

import com.fabrizio.udemy.Repository.PersonRepository;
import com.fabrizio.udemy.entity.Person;
import com.fabrizio.udemy.integrationtest.testcontainer.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository repository;

     @DisplayName("Test for Person  method save")
     @Test
     void testGivenPenson_WhenSave_ThenResturnSavePerson(){
         Person person = new Person();
         person.setFirstName("Fabrizio");
         person.setLastName("Armada");
         person.setAddress("Jujuy");
         person.setGender("Male");
         person.setEmail("s");

         Person personSaved = repository.save(person);
         assertNotNull(personSaved);
         assertTrue(person.getId() > 0);
     }

    @DisplayName("Test for Person  method get persons")
    @Test
    void testGivenPensons_WhenList_ThenResturnListOfPersons(){
        Person person = new Person();
        person.setFirstName("Fabrizio");
        person.setLastName("Armada");
        person.setAddress("Jujuy");
        person.setGender("Male");
        person.setEmail("s");
        Person person2 = new Person();
        person2.setFirstName("Fabrizio2");
        person2.setLastName("Armada22");
        person2.setAddress("Jujuy2");
        person2.setGender("Male");
        person2.setEmail("s2");

        repository.save(person);
        repository.save(person2);

        List<Person> list = repository.findAll();

        assertNotNull(list);
        assertTrue(list.size()>0);
    }

    @DisplayName("Test for Person  method find by id")
    @Test
    void testGivenPenson_WhenFind_ThenResturnPersonObjet(){
        Person person = new Person();
        person.setFirstName("Fabrizio");
        person.setLastName("Armada");
        person.setAddress("Jujuy");
        person.setGender("Male");
        person.setEmail("s");
        repository.save(person);
        Person personFound = repository.findById(person.getId()).get();
        assertNotNull(personFound);
        assertTrue(person.getId() > 0);
    }

}
