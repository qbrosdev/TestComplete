package com.qbros.testcomplete.controller;

import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.utils.Persons;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class PersonJsonTest {

    @Autowired
    JacksonTester<Person> jsonTester;

    @Test
    void deserialize() throws IOException {

        Person expected = Persons.aPersonBuilderWithId(123).withAge(12).withName("name").withGender(GENDER.FEMALE).build();
        Person actual = jsonTester.readObject("person.json");

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void serialize() throws IOException {

        String json = "{\"personId\":{\"id\":123},\"age\":12,\"name\":\"name\",\"gender\":\"FEMALE\"}";

        Person expected = Persons.aPersonBuilderWithId(123).withAge(12).withName("name").withGender(GENDER.FEMALE).build();
        ObjectContent<Person> actual = jsonTester.parse(json);

        assertThat(actual.getObject()).isEqualTo(expected);
    }

    @Test
    void serialize2() throws IOException {

        Person expected = Persons.aPersonBuilderWithId(123).withAge(12).withName("name").withGender(GENDER.FEMALE).build();
        Person actual = jsonTester.readObject("person.json");

        assertThat(actual).isEqualTo(expected);
    }

}
