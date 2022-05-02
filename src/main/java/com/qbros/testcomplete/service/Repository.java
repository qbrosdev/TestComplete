package com.qbros.testcomplete.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Repository<T, ID> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(ID id);

    ID create(T entry);

    T update(T entry);

    void delete(ID id);
}
