package com.example.demo.application.services;

import com.example.demo.application.dto.LoginDto;
import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.application.port.out.SessionRepositoryPort;
import com.example.demo.application.port.out.TokenGeneratorPort;
import com.example.demo.domain.exception.SessionInvalidCredentialsException;
import com.example.demo.domain.model.SessionModel;
import com.example.demo.support.PersonMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServicesTest {

    @Mock
    PersonRepositoryPort personRepository;

    @Mock
    SessionRepositoryPort sessionRepository;

    @Mock
    PasswordEncoderPort passwordEncoder;

    @Mock
    TokenGeneratorPort tokenGenerator;

    AuthServices service;

    @BeforeEach
    void setUp() {
        service = new AuthServices(personRepository, sessionRepository, passwordEncoder, tokenGenerator);
    }

    @Test
    void loginCorrectoCreaUnaSesionYDevuelveElToken() {
        when(personRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(PersonMother.anaGuardada()));
        when(passwordEncoder.matches(PersonMother.PASSWORD, PersonMother.HASH)).thenReturn(true);
        when(tokenGenerator.generate()).thenReturn("token-123");
        when(sessionRepository.create(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LoginDto result = service.login("ana@mail.com", PersonMother.PASSWORD);

        assertEquals("token-123", result.getToken());
        assertEquals(1, result.getPersonId());
        assertEquals("Ana", result.getName());

        ArgumentCaptor<SessionModel> session = ArgumentCaptor.forClass(SessionModel.class);
        verify(sessionRepository).create(session.capture());
        assertEquals("token-123", session.getValue().getToken());
        assertEquals(1, session.getValue().getPersonId());
    }

    @Test
    void loginConEmailInexistenteRechaza() {
        when(personRepository.findByEmail("nadie@mail.com")).thenReturn(Optional.empty());

        SessionInvalidCredentialsException ex = assertThrows(SessionInvalidCredentialsException.class,
                () -> service.login("nadie@mail.com", PersonMother.PASSWORD));

        assertEquals("nadie@mail.com", ex.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(sessionRepository, never()).create(any());
    }

    @Test
    void loginConPasswordIncorrectaRechaza() {
        when(personRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(PersonMother.anaGuardada()));
        when(passwordEncoder.matches("Otra123", PersonMother.HASH)).thenReturn(false);

        assertThrows(SessionInvalidCredentialsException.class, () -> service.login("ana@mail.com", "Otra123"));

        verify(sessionRepository, never()).create(any());
    }

    @Test
    void emailInexistenteYPasswordIncorrectaDanElMismoMensaje() {
        when(personRepository.findByEmail("nadie@mail.com")).thenReturn(Optional.empty());
        when(personRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(PersonMother.anaGuardada()));
        when(passwordEncoder.matches("Otra123", PersonMother.HASH)).thenReturn(false);

        String unknownEmail = assertThrows(SessionInvalidCredentialsException.class,
                () -> service.login("nadie@mail.com", "Otra123")).getMessage();
        String wrongPassword = assertThrows(SessionInvalidCredentialsException.class,
                () -> service.login("ana@mail.com", "Otra123")).getMessage();

        assertEquals(unknownEmail, wrongPassword);
    }

    @Test
    void logoutBorraLaSesionDelToken() {
        service.logout("token-123");

        verify(sessionRepository).deleteByToken("token-123");
    }
}
