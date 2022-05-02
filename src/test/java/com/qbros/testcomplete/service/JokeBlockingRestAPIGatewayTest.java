package com.qbros.testcomplete.service;

import com.qbros.testcomplete.service.models.Joke;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@Import({JokeAPIGatewayTestConfig.class})
class JokeBlockingRestAPIGatewayTest {

    MockWebServer mockWebServer;

    @Autowired
    RestTemplate restTemplate;

    JokeBlockingRestAPIGateway jokeBlockingRestAPIGateway;

    @BeforeEach
    void setUp() throws IOException {
        /*
         * MockWebServer is very light so we can create separate instance of them for each test
         * {@link https://github.com/square/okhttp/tree/master/mockwebserver#example }
         */
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        jokeBlockingRestAPIGateway = new JokeBlockingRestAPIGateway(restTemplate, mockWebServer.url("/").url().toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getFavoriteJoke() throws InterruptedException {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(getJson("joke-response.json")));

        Joke response = jokeBlockingRestAPIGateway.getFavoriteJoke();

        RecordedRequest request = mockWebServer.takeRequest();

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(response.getCategory()).isEqualTo("Programming");
        assertThat(request.getPath()).isEqualTo("/joke/Any?safe-mode&type=single");
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