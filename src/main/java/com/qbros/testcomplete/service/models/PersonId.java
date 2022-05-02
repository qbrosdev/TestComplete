package com.qbros.testcomplete.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

@Value
public class PersonId {

    @Range(min = 100, max = 1000, message = "Id not in range")
    long id;

    @JsonCreator
    public PersonId(@JsonProperty("id") long id) {
        this.id = id;
    }
}
