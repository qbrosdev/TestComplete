package com.qbros.testcomplete.component;

import com.qbros.testcomplete.persistence.PersonDao;
import com.qbros.testcomplete.persistence.PersonEntity;
import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WithRealEnvIntegrationTest {

    static MockWebServer mockWebServer;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PersonDao personDao;
    @Value("${local.server.port}")
    private int port;

    @BeforeAll
    static void setupMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("joke.api.uri", () -> mockWebServer.url("/").url().toString());
    }

    @AfterEach
    void deleteEntities() {
        personDao.deleteAll();
    }

    @Test
    void createPersonTest() {

        HttpEntity<Person> request = new HttpEntity<>(Person.aPerson()
                .withPersonId(new PersonId(123L))
                .withAge(26)
                .withName("dfdf")
                .withGender(GENDER.MALE)
                .build());

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(getJson("joke-response.json")));

        ResponseEntity<String> resp = restTemplate.withBasicAuth("user1", "pass")
                .postForEntity("/persons", request, String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getHeaders().getLocation()).isEqualTo(URI.create("http://localhost:" + this.port + "/persons/" + 123));
    }

    @Test
    void findPersonTest() {

        PersonEntity personEntity = PersonEntity.aPersonEntity()
                .withPersonId(123L)
                .withAge(26)
                .withName("dfdf")
                .withGender(GENDER.MALE)
                .build();

        Person expected = Person.aPerson()
                .withPersonId(new PersonId(123L))
                .withAge(26)
                .withName("dfdf")
                .withGender(GENDER.MALE)
                .build();

        personDao.save(personEntity);

        ResponseEntity<Person> response = restTemplate
                .withBasicAuth("user1", "pass")
                .getForEntity("/persons/123", Person.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    private String getJson(String path) {
        try {
            InputStream jsonStream = this.getClass().getClassLoader().getResourceAsStream(path);
            assert jsonStream != null;
            return new String(jsonStream.readAllBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
