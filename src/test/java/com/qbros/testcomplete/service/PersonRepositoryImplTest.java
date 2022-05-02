package com.qbros.testcomplete.service;

import com.qbros.testcomplete.persistence.PersonDao;
import com.qbros.testcomplete.persistence.PersonEntity;
import com.qbros.testcomplete.service.models.GENDER;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import com.qbros.testcomplete.service.models.PersonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryImplTest {

    @Spy
    PersonMapper personMapper;

    @Mock
    PersonDao personDao;

    @InjectMocks
    PersonRepositoryImpl personRepository;

    @Test
    void findAll() {

        PageRequest page = PageRequest.of(5, 10);
        PersonEntity personEntity1 = PersonEntity.aPersonEntity().withPersonId(1234L).withGender(GENDER.MALE).withAge(25).build();
        PersonEntity personEntity2 = PersonEntity.aPersonEntity().withPersonId(123L).withGender(GENDER.FEMALE).withAge(35).build();

        Person person = Person.aPerson().withPersonId(new PersonId(1234L)).withGender(GENDER.MALE).withAge(25).build();
        Person person2 = Person.aPerson().withPersonId(new PersonId(123L)).withGender(GENDER.FEMALE).withAge(35).build();

        List<PersonEntity> personEntityList = List.of(personEntity1, personEntity2);

        given(personDao.findAll(page)).willReturn(new PageImpl<>(personEntityList, page, 100));

        Page<Person> actual = personRepository.findAll(page);

        assertThat(actual.getPageable()).isEqualTo(page);
        assertThat(actual.get().collect(Collectors.toList())).containsExactlyInAnyOrder(person, person2);
        verify(personMapper, times(2)).toModel(any());
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}