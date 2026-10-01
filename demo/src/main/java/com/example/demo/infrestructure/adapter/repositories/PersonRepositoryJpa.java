package com.example.demo.infrestructure.adapter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepositoryJpa extends JpaRepository<PersonEntity, Integer> {
}
