package com.qbros.testcomplete.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbros.testcomplete.service.JokeAPIGateway;
import com.qbros.testcomplete.service.models.Joke;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.utils.Persons;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class WithMockedEnvIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JokeAPIGateway jokeAPIGatewayMock;

    @Test
    void createPerson() throws Exception {

        Person mockPerson = Persons.aPersonWithId(123);
        Joke mockJoke = new Joke(false, "Social", "Lame joke for test");

        given(jokeAPIGatewayMock.getFavoriteJoke()).willReturn(mockJoke);

        mockMvc.perform(post("/persons")
                        .content(objectMapper.writeValueAsString(mockPerson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/persons/123"))
                .andExpect(content().string("Resource Created"));
    }

}
