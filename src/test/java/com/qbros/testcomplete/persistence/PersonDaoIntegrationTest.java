package com.qbros.testcomplete.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:dbTest.properties")
class PersonDaoIntegrationTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PersonDao personDao;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(personDao).isNotNull();
    }

    @Test
    void findByPersonId() {
        PersonEntity personEntity = PersonEntity.aPersonEntity().withPersonId(1123L).build();
        entityManager.persist(personEntity);
        Optional<PersonEntity> person = personDao.findByPersonId(1123L);
        assertThat(person.isPresent()).isTrue();
    }

    @Test
    @Sql("classpath:createPerson.sql")
    void findByNameOrderByAgeAndName() {
        PageRequest pageRequest = PageRequest.of(2, 5);
        Page<PersonEntity> persons = personDao.findAll(pageRequest);
        assertThat(persons.getPageable()).isEqualTo(pageRequest);
        assertThat(persons).isNotNull();
    }

}