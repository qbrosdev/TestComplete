package com.qbros.testcomplete.utils;

import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Persons {

    static Random random = new Random();

    public static Person.PersonBuilder aPersonBuilderWithId(long id) {
        return Person
                .aPerson()
                .withPersonId(new PersonId(id)).build()
                .toBuilder();
    }

    public static Person aPersonWithId(long id) {
        return aPersonBuilderWithId(id)
                .withGender(GENDER.MALE).withAge(12).withName("ABC")
                .withPersonId(new PersonId(id)).build();
    }

    public static Person.PersonBuilder aPersonBuilder() {
        return aPersonBuilderWithId(123);
    }

    public static Person generatePerson() {
        return aPersonBuilder()
                .withName("ABC")
                .withGender(GENDER.MALE)
                .withAge(26)
                .build();
    }

    public static List<Person> generateMultipleRandom(int count) {
        return IntStream
                .range(0, count)
                .mapToObj(it -> aPersonBuilder()
                        .withName("Name" + it)
                        .withGender(GENDER.values()[random.nextInt(GENDER.values().length)])
                        .build())
                .collect(Collectors.toList());
    }

}
