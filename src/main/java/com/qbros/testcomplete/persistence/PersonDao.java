package com.qbros.testcomplete.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDao extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByPersonId(long personId);
}
