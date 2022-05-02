package com.qbros.testcomplete.utils;


import com.qbros.testcomplete.persistence.PersonEntity;
import com.qbros.testcomplete.service.models.GENDER;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonEntities {

    public static PersonEntity.PersonEntityBuilder aPersonEntityWithId() {
        return PersonEntity
                .aPersonEntity()
                .withPersonId(125)
                .build()
                .toBuilder();
    }

    public static List<PersonEntity> generateMultiple(int count) {
        return IntStream
                .range(0, count)
                .mapToObj(it -> aPersonEntityWithId()
                        .withName("Name" + it)
                        .withGender(GENDER.FEMALE)
                        .build())
                .collect(Collectors.toList());
    }

    public static PersonEntity generateOne() {
        return aPersonEntityWithId()
                .withName("ABC")
                .withGender(GENDER.MALE)
                .withAge(55)
                .build();
    }
}
