package com.qbros.testcomplete.service;

import com.qbros.testcomplete.service.models.Joke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class JokeBlockingRestAPIGateway implements JokeAPIGateway {

    private final RestTemplate restTemplate;
    private final String url;

    public JokeBlockingRestAPIGateway(RestTemplate restTemplate, @Value("${joke.api.uri}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public Joke getFavoriteJoke() {
        //"https://v2.jokeapi.dev/joke/Any?safe-mode&type=single"
        return restTemplate.getForEntity(url + "/joke/Any?safe-mode&type=single", Joke.class).getBody();
    }
}
