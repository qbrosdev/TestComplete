package com.qbros.testcomplete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbros.testcomplete.service.PersonService;
import com.qbros.testcomplete.service.models.*;
import com.qbros.testcomplete.utils.Persons;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
class PersonControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PersonService personService;

    @Nested
    @DisplayName("Get All Persons paged")
    class GetAllPersonsPagedTests {

        @Test
        @DisplayName("Get All Persons paged")
        void getPersons() throws Exception {

            Person person = Person.aPerson().withName("ABC").withGender(GENDER.MALE).withAge(26).build();
            Person person2 = Person.aPerson().withName("ABfC").withGender(GENDER.FEMALE).withAge(76).build();

            PageImpl<Person> expected = new PageImpl<>(List.of(person, person2));

            given(personService.getAll(0, 10)).willReturn(expected);

            mockMvc.perform(get("/persons"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    @DisplayName("Finding Person by id")
    class GetByIdTests {

        @Test
        void getPersonById() throws Exception {

            Person mockPerson = Persons.aPersonBuilderWithId(123).build();

            given(personService.getById(any(PersonId.class))).willReturn(mockPerson);

            mockMvc.perform(get("/persons/{id}", 123))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(mockPerson)));
        }

        @Test
        void getPersonById_invalidPerson() throws Exception {

            mockMvc.perform(get("/persons/{id}", 1))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void getPersonById_systemException() throws Exception {

            given(personService.getById(any(PersonId.class))).willThrow(new SystemException("Some Err Msg", ServiceErrCode.SERVICE_TIMEOUT));

            mockMvc.perform(get("/persons/{id}", 123))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.message").value("Some Err Msg"))
                    .andExpect(jsonPath("$.attributes['Err Code']").value(ServiceErrCode.SERVICE_TIMEOUT.name()));
        }
    }

    @Nested
    @DisplayName("Creating Person")
    class CreateTests {

        @Test
        @DisplayName("With valid input")
        void create() throws Exception {

            Person mockPerson = Persons.aPersonWithId(123);

            given(personService.create(any(Person.class))).willReturn(new PersonId(123));

            mockMvc.perform(post("/persons")
                            .content(objectMapper.writeValueAsString(mockPerson))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "http://localhost/persons/123"))
                    .andExpect(content().string("Resource Created"));
        }

        @Test
        @DisplayName("With in-valid data")
        void create_withInvalidInput() throws Exception {

            Person mockPerson = Persons.aPersonBuilderWithId(12L).build();

            mockMvc.perform(post("/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockPerson)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Update person")
    class UpdateTests {

        @Test
        void update() {
        }
    }

}