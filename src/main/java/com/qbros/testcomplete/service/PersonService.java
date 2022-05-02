package com.qbros.testcomplete.service;


import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import org.springframework.data.domain.Page;

public interface PersonService {

    Page<Person> getAll(int offset, int size);

    Person getById(PersonId id);

    PersonId create(Person person);

    Person update(Person person);
}
