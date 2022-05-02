package com.qbros.testcomplete.service;

import com.qbros.testcomplete.persistence.PersonEntity;
import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import com.qbros.testcomplete.service.models.PersonMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.qbros.testcomplete.utils.Persons.aPersonBuilderWithId;
import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    PersonMapper personMapper = new PersonMapper();

    @Test
    void toEntity() {
        PersonEntity actual = personMapper.of(aPersonBuilderWithId(123).withAge(25).withName("ABC").withGender(GENDER.MALE).build());
        assertThat(actual).isEqualTo(PersonEntity
                .aPersonEntity()
                .withPersonId(123).withAge(25).withName("ABC")
                .withGender(GENDER.MALE).build());
    }

    @Test
    void toModel() {

        Person actual = personMapper
                .toModel(PersonEntity
                        .aPersonEntity()
                        .withPersonId(123).withAge(25).withName("ABC")
                        .withGender(GENDER.MALE).build());

        Person expected = aPersonBuilderWithId(123).withAge(25).withName("ABC").withGender(GENDER.MALE).build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void listOfEntities() {

        Person expected1 = Person.aPerson()
                .withPersonId(new PersonId(123)).withAge(35).withName("ABC")
                .withGender(GENDER.FEMALE).build();

        Person expected2 = Person.aPerson()
                .withPersonId(new PersonId(123)).withAge(25).withName("ABC23")
                .withGender(GENDER.MALE).build();

        PersonEntity personEntity = PersonEntity.aPersonEntity()
                .withPersonId(123).withAge(35).withName("ABC")
                .withGender(GENDER.FEMALE).build();

        PersonEntity personEntity2 = PersonEntity.aPersonEntity()
                .withPersonId(123).withAge(25).withName("ABC23")
                .withGender(GENDER.MALE).build();

        List<Person> actual = personMapper.ofEntities(List.of(personEntity, personEntity2));
        assertThat(actual).containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    void listOfModels() {

        Person person = Person.aPerson()
                .withPersonId(new PersonId(123)).withAge(35).withName("ABC")
                .withGender(GENDER.FEMALE).build();

        Person person2 = Person.aPerson()
                .withPersonId(new PersonId(123)).withAge(25).withName("ABC23")
                .withGender(GENDER.MALE).build();

        PersonEntity expected = PersonEntity.aPersonEntity()
                .withPersonId(123).withAge(35).withName("ABC")
                .withGender(GENDER.FEMALE).build();

        PersonEntity expected2 = PersonEntity.aPersonEntity()
                .withPersonId(123).withAge(25).withName("ABC23")
                .withGender(GENDER.MALE).build();

        List<PersonEntity> actual = personMapper.ofModels(List.of(person, person2));
        assertThat(actual).containsExactlyInAnyOrder(expected, expected2);
    }
}