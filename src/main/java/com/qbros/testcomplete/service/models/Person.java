package com.qbros.testcomplete.service.models;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Data
@Builder(builderMethodName = "aPerson", setterPrefix = "with", toBuilder = true)
public class Person {

    @Valid
    private PersonId personId;
    @Positive
    private int age;
    @Length(min = 2)
    private String name;
    private GENDER gender;
    private String favJoke;

}
