package com.example.demo.infrestructure.adapter.repositories.person;

import com.example.demo.application.services.PersonServices;
import com.example.demo.domain.exception.InvalidValueException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.infrestructure.adapter.security.BCryptPasswordEncoderAdapter;
import com.example.demo.support.PersonMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;IGNORECASE=TRUE;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryAdapterH2IT {

    @Autowired
    PersonRepositoryJpa repositoryJpa;

    PersonRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new PersonRepositoryAdapter(repositoryJpa);
    }

    @Test
    void createYGetByIdDevuelvenLosMismosDatos() {
        PersonModel saved = adapter.create(PersonMother.ana());

        PersonModel found = adapter.getById(saved.getId());

        assertTrue(saved.getId() > 0);
        assertEquals("Ana", found.getName());
        assertEquals("ana@mail.com", found.getEmail());
        assertEquals(PersonMother.PASSWORD, found.getPassword());
        assertEquals("77712345", found.getPhone());
    }

    @Test
    void getAllDevuelveTodasLasPersonas() {
        adapter.create(PersonMother.ana());
        adapter.create(PersonMother.luis());

        assertEquals(2, adapter.getAll().size());
    }

    @Test
    void getByIdInexistenteLanzaNotFound() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> adapter.getById(999));

        assertEquals("persona", ex.getEntity());
        assertEquals(999, ex.getId());
    }

    @Test
    void updateModificaLosDatos() {
        PersonModel saved = adapter.create(PersonMother.ana());

        adapter.update(saved.update("Ana Maria", "ana@mail.com", "Nueva123", "77799999"));

        PersonModel found = adapter.getById(saved.getId());
        assertEquals("Ana Maria", found.getName());
        assertEquals("77799999", found.getPhone());
    }

    @Test
    void updateDeIdInexistenteLanzaNotFound() {
        assertThrows(NotFoundException.class,
                () -> adapter.update(new PersonModel(999, "Ana", "ana@mail.com", PersonMother.PASSWORD, "77712345")));
    }

    @Test
    void deleteBorraLaPersona() {
        PersonModel saved = adapter.create(PersonMother.ana());

        adapter.delete(saved.getId());

        assertThrows(NotFoundException.class, () -> adapter.getById(saved.getId()));
    }

    @Test
    void deleteDeIdInexistenteLanzaNotFound() {
        assertThrows(NotFoundException.class, () -> adapter.delete(999));
    }

    @Test
    void existsByEmailNoDistingueMayusculas() {
        adapter.create(PersonMother.ana());

        assertTrue(adapter.existsByEmail("ana@mail.com"));
        assertTrue(adapter.existsByEmail("ANA@mail.com"));
        assertFalse(adapter.existsByEmail("nadie@mail.com"));
    }

    @Test
    void findByEmailDevuelveLaPersonaOVacio() {
        adapter.create(PersonMother.ana());

        assertEquals("Ana", adapter.findByEmail("ana@mail.com").orElseThrow().getName());
        assertTrue(adapter.findByEmail("nadie@mail.com").isEmpty());
    }

    @Test
    void laColumnaEmailNoPermiteRepetidos() {
        adapter.create(PersonMother.ana());

        assertThrows(DataIntegrityViolationException.class,
                () -> adapter.create(new PersonModel(0, "Otra Ana", "ana@mail.com", "Clave789", "77700000")));
    }

    @Test
    void createRechazaDatosInvalidosSinGuardar() {
        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> adapter.create(new PersonModel(0, "Ana", "ana@mail.com", PersonMother.PASSWORD, "abc")));

        assertEquals("phone", ex.getField());
        assertEquals(0, repositoryJpa.count());
    }

    @Test
    @Disabled
    void unaPasswordInvalidaNoDeberiaGuardarse() {
        PersonServices service = new PersonServices(adapter, new BCryptPasswordEncoderAdapter());

        assertThrows(InvalidValueException.class,
                () -> service.create(new PersonModel(0, "Ana", "ana@mail.com", "123", "77712345")));
    }
}
