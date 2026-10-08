package com.example.demo.infrestructure.adapter.repositories.session;

import com.example.demo.domain.model.PersonModel;
import com.example.demo.domain.model.SessionModel;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryAdapter;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryJpa;
import com.example.demo.support.MySqlContainerConfig;
import com.example.demo.support.PersonMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MySqlContainerConfig.class)
@Testcontainers(disabledWithoutDocker = true)
class SessionRepositoryAdapterIT {

    @Autowired
    SessionRepositoryJpa sessionRepositoryJpa;

    @Autowired
    PersonRepositoryJpa personRepositoryJpa;

    @Autowired
    TestEntityManager entityManager;

    SessionRepositoryAdapter adapter;
    PersonRepositoryAdapter personAdapter;
    PersonModel ana;

    @BeforeEach
    void setUp() {
        adapter = new SessionRepositoryAdapter(sessionRepositoryJpa, personRepositoryJpa);
        personAdapter = new PersonRepositoryAdapter(personRepositoryJpa);
        ana = personAdapter.create(PersonMother.ana());
    }

    @Test
    void createGuardaLaSesionDeLaPersona() {
        SessionModel saved = adapter.create(SessionModel.start("token-1", ana.getId()));

        assertTrue(saved.getId() > 0);
        assertEquals("token-1", saved.getToken());
        assertEquals(ana.getId(), saved.getPersonId());
    }

    @Test
    void deleteByTokenSoloBorraEsaSesion() {
        adapter.create(SessionModel.start("token-1", ana.getId()));
        adapter.create(SessionModel.start("token-2", ana.getId()));

        adapter.deleteByToken("token-1");

        assertEquals(1, sessionRepositoryJpa.count());
    }

    @Test
    void deleteByTokenInexistenteNoFalla() {
        assertDoesNotThrow(() -> adapter.deleteByToken("no-existe"));
    }

    @Test
    void laLlaveForaneaRechazaUnaPersonaInexistente() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            adapter.create(SessionModel.start("token-1", 999));
            entityManager.flush();
        });
    }

    @Test
    void elTokenNoPuedeRepetirse() {
        adapter.create(SessionModel.start("token-1", ana.getId()));

        assertThrows(DataIntegrityViolationException.class, () -> {
            adapter.create(SessionModel.start("token-1", ana.getId()));
            entityManager.flush();
        });
    }

    @Test
    void alBorrarLaPersonaSeBorranSusSesiones() {
        adapter.create(SessionModel.start("token-1", ana.getId()));
        adapter.create(SessionModel.start("token-2", ana.getId()));
        entityManager.flush();
        entityManager.clear();

        personAdapter.delete(ana.getId());
        entityManager.flush();
        entityManager.clear();

        assertEquals(0, sessionRepositoryJpa.count());
    }
}
