package com.example.demo.application.services;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.exception.PersonAlreadyExistsException;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.support.PersonMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @Mock
    PersonRepositoryPort personRepository;

    @Mock
    PasswordEncoderPort passwordEncoder;

    PersonServices service;

    @BeforeEach
    void setUp() {
        service = new PersonServices(personRepository, passwordEncoder);
    }

    @Test
    void getAllDevuelveLasPersonasComoDto() {
        when(personRepository.getAll()).thenReturn(List.of(PersonMother.anaGuardada()));

        List<PersonDto> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("ana@mail.com", result.get(0).getEmail());
    }

    @Test
    void getByIdPropagaNotFound() {
        when(personRepository.getById(99)).thenThrow(new NotFoundException("persona", 99));

        assertThrows(NotFoundException.class, () -> service.getById(99));
    }

    @Test
    void createGuardaLaPasswordEncriptada() {
        when(personRepository.existsByEmail("ana@mail.com")).thenReturn(false);
        when(passwordEncoder.encode(PersonMother.PASSWORD)).thenReturn(PersonMother.HASH);
        when(personRepository.create(any())).thenReturn(PersonMother.anaGuardada());

        PersonDto result = service.create(PersonMother.ana());

        ArgumentCaptor<PersonModel> saved = ArgumentCaptor.forClass(PersonModel.class);
        verify(personRepository).create(saved.capture());
        assertEquals(PersonMother.HASH, saved.getValue().getPassword());
        assertEquals("ana@mail.com", saved.getValue().getEmail());
        assertEquals(1, result.getId());
    }

    @Test
    void createRechazaEmailRepetido() {
        when(personRepository.existsByEmail("ana@mail.com")).thenReturn(true);

        PersonAlreadyExistsException ex = assertThrows(PersonAlreadyExistsException.class,
                () -> service.create(PersonMother.ana()));

        assertEquals("email", ex.getField());
        verify(passwordEncoder, never()).encode(anyString());
        verify(personRepository, never()).create(any());
    }

    @Test
    void updateConElMismoEmailNoConsultaSiExiste() {
        when(personRepository.getById(1)).thenReturn(PersonMother.anaGuardada());
        when(passwordEncoder.encode("Nueva123")).thenReturn("NUEVO_HASH");
        when(personRepository.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.update(1, new PersonModel(0, "Ana Maria", "ANA@mail.com", "Nueva123", "77712345"));

        verify(personRepository, never()).existsByEmail(anyString());
        ArgumentCaptor<PersonModel> saved = ArgumentCaptor.forClass(PersonModel.class);
        verify(personRepository).update(saved.capture());
        assertEquals(1, saved.getValue().getId());
        assertEquals("Ana Maria", saved.getValue().getName());
        assertEquals("NUEVO_HASH", saved.getValue().getPassword());
    }

    @Test
    void updateConEmailNuevoLibreGuarda() {
        when(personRepository.getById(1)).thenReturn(PersonMother.anaGuardada());
        when(personRepository.existsByEmail("ana.maria@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("Nueva123")).thenReturn("NUEVO_HASH");
        when(personRepository.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PersonDto result = service.update(1, new PersonModel(0, "Ana", "ana.maria@mail.com", "Nueva123", "77712345"));

        assertEquals("ana.maria@mail.com", result.getEmail());
    }

    @Test
    void updateConEmailNuevoOcupadoRechaza() {
        when(personRepository.getById(1)).thenReturn(PersonMother.anaGuardada());
        when(personRepository.existsByEmail("luis@mail.com")).thenReturn(true);

        assertThrows(PersonAlreadyExistsException.class,
                () -> service.update(1, new PersonModel(0, "Ana", "luis@mail.com", "Nueva123", "77712345")));

        verify(personRepository, never()).update(any());
    }

    @Test
    void updateDeIdInexistenteNoGuarda() {
        when(personRepository.getById(99)).thenThrow(new NotFoundException("persona", 99));

        assertThrows(NotFoundException.class, () -> service.update(99, PersonMother.ana()));

        verify(personRepository, never()).update(any());
    }

    @Test
    void deleteDelegaEnElRepositorio() {
        service.delete(1);

        verify(personRepository).delete(1);
    }
}
