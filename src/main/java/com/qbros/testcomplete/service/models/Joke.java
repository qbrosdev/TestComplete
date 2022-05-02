package com.qbros.testcomplete.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Joke {

    boolean error;
    String category;
    String joke;

    @JsonCreator
    public Joke(@JsonProperty("error") boolean error, @JsonProperty("category") String category, @JsonProperty("joke") String joke) {
        this.error = error;
        this.category = category;
        this.joke = joke;
    }
}
