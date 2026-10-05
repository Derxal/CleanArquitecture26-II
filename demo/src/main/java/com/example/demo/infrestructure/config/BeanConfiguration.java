package com.example.demo.infrestructure.config;

import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.application.services.PersonServices;
import com.example.demo.infrestructure.adapter.repositories.PersonRepositoryAdapter;
import com.example.demo.infrestructure.adapter.repositories.PersonRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonRepositoryPort personRepositoryPort(PersonRepositoryJpa repositoryJpa) {
        return new PersonRepositoryAdapter(repositoryJpa);
    }

    @Bean
    public PersonServices personServices(PersonRepositoryPort personRepositoryPort) {
        return new PersonServices(personRepositoryPort);
    }
}
