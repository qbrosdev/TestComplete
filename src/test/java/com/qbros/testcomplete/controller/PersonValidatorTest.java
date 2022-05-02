package com.qbros.testcomplete.controller;


import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.utils.Persons;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonValidatorTest {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void invalidPersonId() {
        Person request = Persons.aPersonBuilderWithId(12L).withAge(123).withName("name").withGender(GENDER.FEMALE).build();
        Set<ConstraintViolation<Person>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void invalidAge() {
        Person request = Persons.aPersonBuilder().withAge(-123).withName("name").withGender(GENDER.FEMALE).build();
        Set<ConstraintViolation<Person>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
    }
}
