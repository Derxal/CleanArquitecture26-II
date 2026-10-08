package com.example.demo.infrestructure.adapter.repositories.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepositoryJpa extends JpaRepository<PersonEntity, Integer> {

    boolean existsByEmail(String email);

    Optional<PersonEntity> findByEmail(String email);
}
