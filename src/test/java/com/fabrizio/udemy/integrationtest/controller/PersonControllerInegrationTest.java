package com.fabrizio.udemy.integrationtest.controller;

import com.fabrizio.udemy.config.TestConfig;
import com.fabrizio.udemy.entity.Person;
import com.fabrizio.udemy.integrationtest.testcontainer.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PersonControllerInegrationTest extends AbstractIntegrationTest {


      private static RequestSpecification specification;
      private static ObjectMapper mapper;
      private static Person person;

      @BeforeAll
      public static void setup(){
          mapper = new ObjectMapper();
          mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

         specification = new RequestSpecBuilder()
                 .setBasePath("/person")
                 .setPort(TestConfig.SERVER_PORT)
                 .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                 .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                 .build();
         person = new Person();
            person.setFirstName("Fabrizio");
            person.setLastName("Armada");
            person.setAddress("Jujuy");
            person.setEmail("user@test.com");
            person.setGender("Male");
      }

      @DisplayName("Method create person test")
      @Order(1)
      @Test
      void integrationTestGivenPersonObject_When_CreateOnePerson_ShouldReturnPersonObject() throws JsonProcessingException {

               var content = given().spec(specification)
                       .contentType(TestConfig.CONTENT_TYPE_JSON)
                       .body(person)
                       .when()
                       .post("/create")
                       .then()
                       .statusCode(201)
                       .extract()
                       .body()
                       .asString();

               Person createdPerson = mapper.readValue(content, Person.class);
               person = createdPerson;

                   assertNotNull(createdPerson);
                   assertNotNull(createdPerson.getId());
                   assertNotNull(createdPerson.getFirstName());
                   assertNotNull( createdPerson.getLastName());
                   assertNotNull(createdPerson.getAddress());
                   assertNotNull(createdPerson.getEmail());
                   assertNotNull(createdPerson.getGender());

                   assertTrue(createdPerson.getId() > 0);
                   assertEquals("Fabrizio", createdPerson.getFirstName());
                   assertEquals("Armada", createdPerson.getLastName());
                   assertEquals("Jujuy", createdPerson.getAddress());
                   assertEquals("user@test.com", createdPerson.getEmail());
                   assertEquals("Male", createdPerson.getGender());
      }

      @DisplayName("integration test given updated person objecto when update one person then should return updated person object")
      @Order(2)
      @Test
      public void integrationTestGivenUpdatedPerson_WhenUpdatePerson_ThenShouldReturnUpdatedPersonObject() throws JsonProcessingException {
          person.setFirstName("FabrizioEdit");
          person.setLastName("ArmadaEdit");
          person.setAddress("JujuyEdit");
          person.setEmail("userEdit@test.com");
          person.setGender("Male");

          var content = given().spec(specification)
                  .contentType(TestConfig.CONTENT_TYPE_JSON)
                  .body(person)
                  .when()
                  .put("/{id}", person.getId())
                  .then()
                  .statusCode(200)
                  .extract()
                  .body()
                  .asString();

          Person updatedPerson = mapper.readValue(content, Person.class);

            assertNotNull(updatedPerson);

            assertEquals(person.getId(), updatedPerson.getId());
            assertEquals("FabrizioEdit", updatedPerson.getFirstName());
            assertEquals("ArmadaEdit", updatedPerson.getLastName());
            assertEquals("JujuyEdit", updatedPerson.getAddress());
            assertEquals("userEdit@test.com", updatedPerson.getEmail());
            assertEquals("Male", updatedPerson.getGender());

    }

    @DisplayName("integration test given find by id a person object when find by id one person then should return person object")
    @Order(3)
    @Test
    public void integrationTestGivenFindByIdPerson_WhenFindById_ThenShouldReturnPersonObject() throws JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .get("/{id}", person.getId())
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person foundPerson= mapper.readValue(content, Person.class);

        assertNotNull(foundPerson);

        assertEquals("FabrizioEdit", foundPerson.getFirstName());
        assertEquals("ArmadaEdit", foundPerson.getLastName());
        assertEquals("JujuyEdit", foundPerson.getAddress());
        assertEquals("userEdit@test.com", foundPerson.getEmail());
        assertEquals("Male", foundPerson.getGender());

    }

    @DisplayName("integration test given find all persons when find all the should return all existing persons")
    @Order(4)
    @Test
    public void integrationTestGivenFindAllPersons_WhenFindAll_ThenShouldReturnPersons() throws JsonProcessingException {

          Person person2 = new Person();
          person2.setFirstName("Fabrizio2");
          person2.setLastName("Armada2");
          person2.setAddress("Jujuy2");
          person2.setEmail("userTest2@test.com");
          person2.setGender("Male");

          given().spec(specification)
                  .contentType(TestConfig.CONTENT_TYPE_JSON)
                  .body(person2)
                  .when()
                  .post("/create")
                  .then()
                  .statusCode(201)
                  .extract()
                  .body()
                  .asString();

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

       List<Person> persons = Arrays.asList(mapper.readValue(content, Person[].class));

        assertNotNull(persons);
        assertTrue(persons.size()  > 1);
    }

    @DisplayName("integration test given delete person when delete a person the should delete the person")
    @Order(5)
    @Test
    public void integrationTestGivenDeletePerson_WhenDelete_ThenShouldDeletePerson(){
              given().spec(specification)
                      .contentType(TestConfig.CONTENT_TYPE_JSON)
                      .when()
                      .delete("/{id}", person.getId())
                      .then()
                      .statusCode(204);
    }

}
