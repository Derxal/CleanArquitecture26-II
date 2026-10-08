package com.example.demo.infrestructure.config;

import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.application.port.out.SessionRepositoryPort;
import com.example.demo.application.port.out.TokenGeneratorPort;
import com.example.demo.application.services.AuthServices;
import com.example.demo.application.services.PersonServices;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryAdapter;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryJpa;
import com.example.demo.infrestructure.adapter.repositories.session.SessionRepositoryAdapter;
import com.example.demo.infrestructure.adapter.repositories.session.SessionRepositoryJpa;
import com.example.demo.infrestructure.adapter.security.BCryptPasswordEncoderAdapter;
import com.example.demo.infrestructure.adapter.security.SecureTokenGeneratorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonRepositoryPort personRepositoryPort(PersonRepositoryJpa repositoryJpa) {
        return new PersonRepositoryAdapter(repositoryJpa);
    }

    @Bean
    public SessionRepositoryPort sessionRepositoryPort(SessionRepositoryJpa repositoryJpa, PersonRepositoryJpa personRepositoryJpa) {
        return new SessionRepositoryAdapter(repositoryJpa, personRepositoryJpa);
    }

    @Bean
    public PasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter();
    }

    @Bean
    public TokenGeneratorPort tokenGeneratorPort() {
        return new SecureTokenGeneratorAdapter();
    }

    @Bean
    public PersonServices personServices(PersonRepositoryPort personRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        return new PersonServices(personRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public AuthServices authServices(PersonRepositoryPort personRepositoryPort, SessionRepositoryPort sessionRepositoryPort,
                                     PasswordEncoderPort passwordEncoderPort, TokenGeneratorPort tokenGeneratorPort) {
        return new AuthServices(personRepositoryPort, sessionRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }
}
