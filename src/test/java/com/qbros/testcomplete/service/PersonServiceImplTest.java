package com.qbros.testcomplete.service;

import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.utils.Persons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    List<Person> personList = Persons.generateMultipleRandom(2);

    @Test
    void getAll() {
        PageRequest page = PageRequest.of(5, 10);
        given(personRepository.findAll(page)).willReturn(new PageImpl<>(personList, page, 100));
        assertThat(personService.getAll(5, 10)).isEqualTo(new PageImpl<>(personList, page, 100));
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}