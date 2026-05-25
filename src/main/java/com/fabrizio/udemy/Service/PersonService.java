package com.fabrizio.udemy.Service;

import com.fabrizio.udemy.entity.Person;

import java.util.List;

public interface PersonService {
    public List<Person> getAllPersons();
    public Person getPerson(Long id);
    public Person updatePerson(Long id, Person personUpdated);
    public void deletePerson(Long id);
    public Person createPerson(Person person);

}
