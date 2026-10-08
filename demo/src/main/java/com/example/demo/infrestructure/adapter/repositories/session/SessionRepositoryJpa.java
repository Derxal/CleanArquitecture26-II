package com.example.demo.infrestructure.adapter.repositories.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SessionRepositoryJpa extends JpaRepository<SessionEntity, Integer> {

    @Transactional
    void deleteByToken(String token);
}
