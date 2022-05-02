package com.qbros.testcomplete.service;

import com.qbros.testcomplete.persistence.PersonDao;
import com.qbros.testcomplete.persistence.PersonEntity;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import com.qbros.testcomplete.service.models.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonMapper personMapper;
    private final PersonDao personDao;

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personDao
                .findAll(pageable)
                .map(personMapper::toModel);
    }

    @Override
    public Optional<Person> findById(PersonId id) {
        return personDao
                .findById(id.getId())
                .map(personMapper::toModel);
    }

    @Override
    public PersonId create(Person entry) {
        PersonEntity savedEntity = personDao.save(personMapper.of(entry));
        return personMapper.toModel(savedEntity).getPersonId();
    }

    @Override
    public Person update(Person entry) {
        return null;
    }

    @Override
    public void delete(PersonId personId) {

    }
}
