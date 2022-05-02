package com.qbros.testcomplete.service;

import com.qbros.testcomplete.service.models.Joke;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import com.qbros.testcomplete.service.models.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.qbros.testcomplete.service.models.ServiceErrCode.ALREADY_EXITS;
import static com.qbros.testcomplete.service.models.ServiceErrCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final JokeAPIGateway jokeAPIGateway;

    @Override
    public Page<Person> getAll(int offset, int size) {
        return personRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public Person getById(PersonId id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new SystemException(NOT_FOUND));
    }

    @Override
    public PersonId create(Person person) {

        if (personRepository.findById(person.getPersonId()).isPresent()) {
            throw new SystemException(ALREADY_EXITS);
        }

        Joke favJoke = jokeAPIGateway.getFavoriteJoke();
        person.setFavJoke(favJoke.getJoke());
        return personRepository.create(person);
    }

    @Override
    public Person update(Person person) {
        return null;
    }
}
