package com.qbros.testcomplete.controller;

import com.qbros.testcomplete.service.PersonService;
import com.qbros.testcomplete.service.models.Person;
import com.qbros.testcomplete.service.models.PersonId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Page<Person> getPersons(@RequestParam(defaultValue = "0") int offset,
                                   @RequestParam(defaultValue = "10") int size) {

        return personService.getAll(offset, size);
    }

    @GetMapping("{id}")
    public Person getPersonById(@Valid PersonId personId) {

        return personService.getById(personId);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody Person person, UriComponentsBuilder uriComponentsBuilder) {

        PersonId createdPersonId = personService.create(person);
        URI location = uriComponentsBuilder.path("/persons/{id}").buildAndExpand(createdPersonId.getId()).toUri();
        return ResponseEntity.created(location).body("Resource Created");
    }

    @PutMapping("{id}")
    public Person update(@Valid PersonId personId, @Valid @RequestBody Person person) {

        return personService.update(person);
    }
}
