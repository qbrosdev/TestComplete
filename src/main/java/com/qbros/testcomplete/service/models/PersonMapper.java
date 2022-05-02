package com.qbros.testcomplete.service.models;

import com.qbros.testcomplete.persistence.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    public PersonEntity of(Person person) {
        return new PersonEntity(person.getPersonId().getId(), person.getName(), person.getAge(), person.getGender());
    }

    public List<PersonEntity> ofModels(List<Person> persons) {
        return persons.stream().map(this::of).collect(Collectors.toList());
    }

    public Person toModel(PersonEntity personEntity) {
        return Person.aPerson()
                .withPersonId(new PersonId(personEntity.getPersonId()))
                .withAge(personEntity.getAge())
                .withName(personEntity.getName())
                .withGender(personEntity.getGender())
                .build();
    }

    public List<Person> ofEntities(List<PersonEntity> persons) {
        return persons.stream().map(this::toModel).collect(Collectors.toList());
    }
}
