package com.fabrizio.udemy.Service;

import com.fabrizio.udemy.Repository.PersonRepository;
import com.fabrizio.udemy.entity.Person;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository repository;

    @Override
    public List<Person> getAllPersons() {
        return repository.findAll();
    }

    @Override
    public Person getPerson(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Person updatePerson(Long id, Person personUpdated) {
        Person personEdit = repository.findById(id).orElse(null);
        if(personEdit != null){
            personEdit.setFirstName(personUpdated.getFirstName());
            personEdit.setLastName(personUpdated.getLastName());
            personEdit.setAddress(personUpdated.getAddress());
            personEdit.setGender(personUpdated.getGender());
            personEdit.setEmail(personUpdated.getEmail());
            return repository.save(personEdit);
        }
        return personEdit;
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
      repository.deleteById(id);
    }

 @Override
 @Transactional
 public Person createPerson(Person person) {
     return repository.save(person);
 }
}
