package com.fabrizio.udemy.controller;

import com.fabrizio.udemy.Service.PersonService;
import com.fabrizio.udemy.entity.Person;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons(){

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(service.getPerson(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPerson(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personUpdated) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updatePerson(id, personUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        service.deletePerson(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}