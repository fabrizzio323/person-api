package com.fabrizio.udemy.service;

import com.fabrizio.udemy.Repository.PersonRepository;
import com.fabrizio.udemy.Service.PersonServiceImpl;
import com.fabrizio.udemy.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest  {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServiceImpl services;

    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person();
        person.setId(1L);
        person.setFirstName("Fabrizio");
        person.setLastName("Armada");
        person.setAddress("Jujuy");
        person.setGender("Male");
        person.setEmail("fabri@test.com");
    }

    @DisplayName("Test given person object when save person then return person object")
    @Test
    public void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject(){

        given(repository.save(person)).willReturn(person);

        Person savedPerson = services.createPerson(person);

        assertNotNull(savedPerson);
        assertEquals(person.getFirstName(), savedPerson.getFirstName());
    }

    @DisplayName("Test get all persons returns list")
    @Test
    public void testGetAllPersons_WhenCalled_thenReturnPersonList(){
        Person person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Ana");
        person2.setLastName("Lopez");
        person2.setAddress("Salta");
        person2.setGender("Female");
        person2.setEmail("ana@test.com");

        given(repository.findAll()).willReturn(Arrays.asList(person, person2));

        List<Person> persons = services.getAllPersons();

        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @DisplayName("Test get person by id returns person")
    @Test
    public void testGetPersonById_WhenExists_thenReturnPerson(){
        given(repository.findById(1L)).willReturn(Optional.of(person));

        Person found = services.getPerson(1L);

        assertNotNull(found);
        assertEquals(person.getFirstName(), found.getFirstName());
    }

    @DisplayName("Test update person when exists then return updated person")
    @Test
    public void testUpdatePerson_WhenExists_thenReturnUpdatedPerson(){
        Person personUpdated = new Person();
        personUpdated.setFirstName("Fabi");
        personUpdated.setLastName("Arm");
        personUpdated.setAddress("Tucuman");
        personUpdated.setGender("Male");
        personUpdated.setEmail("fab_updated@test.com");

        given(repository.findById(1L)).willReturn(Optional.of(person));
        given(repository.save(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));

        Person result = services.updatePerson(1L, personUpdated);

        assertNotNull(result);
        assertEquals(personUpdated.getFirstName(), result.getFirstName());
        assertEquals(personUpdated.getEmail(), result.getEmail());
    }

    @DisplayName("Test delete person invokes repository delete")
    @Test
    public void testDeletePerson_WhenCalled_thenRepositoryDeleteInvoked(){
        services.deletePerson(1L);

        verify(repository).deleteById(1L);
    }

}
