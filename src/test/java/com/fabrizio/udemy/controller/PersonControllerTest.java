package com.fabrizio.udemy.controller;

import com.fabrizio.udemy.Service.PersonService;
import com.fabrizio.udemy.entity.Person;
import com.fabrizio.udemy.integrationtest.testcontainer.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Autowired
    private ObjectMapper mapper;

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

    @DisplayName("Test given person object when create person then return saved person")
    @Test
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws Exception {
        given(service.createPerson(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.address", is(person.getAddress())));
     }

     @DisplayName("Test get all persons returns list")
     @Test
      void testGivenListPerson_WhenListPerson_thenReturnListPersons() throws Exception {
         List<Person> persons = new ArrayList<>();
         persons.add(person);

         given(service.getAllPersons()).willReturn(persons);

         ResultActions response = mockMvc.perform(get("/person"));

         response.andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.size()", is(persons.size())));
     }

     @DisplayName("test get person by id returns person object")
     @Test
    void testGivenPersonObject_WhenFirstById_ThenReturnPersonObject() throws Exception {

        long personId = 1L;
        given(service.getPerson(personId)).willReturn(person);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.address", is(person.getAddress())));

     }

     @DisplayName("Test update person returns updated person")
     @Test
     void TestGivenPersonUpdated_WhenUpdatePerson_ThenReturnPersonUpdated() throws Exception {
            long personId = 1L;
            Person personUpdated = new Person();
            personUpdated.setId(personId);
            personUpdated.setFirstName("Fabi");
            personUpdated.setLastName("Arm");
            personUpdated.setAddress("Tucuman");
            personUpdated.setGender("Male");
            personUpdated.setEmail("fabi.updated@test.com");

            given(service.updatePerson(eq(personId), any(Person.class))).willReturn(personUpdated);

            ResultActions response = mockMvc.perform(put("/person/{id}", personId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(personUpdated)));

            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", is(personUpdated.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(personUpdated.getLastName())))
                    .andExpect(jsonPath("$.address", is(personUpdated.getAddress())))
                    .andExpect(jsonPath("$.email", is(personUpdated.getEmail())));
     }

     @DisplayName("Test delete person invokes service delete")
     @Test
     void TestGivenPerson_WhenDeletePerson_ThenInvokeDelete() throws Exception {
            long personId = 1L;

            willDoNothing().given(service).deletePerson(personId);

            ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

            response.andDo(print())
                    .andExpect(status().isNoContent());

     }

}
