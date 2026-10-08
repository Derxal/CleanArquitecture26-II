package com.example.demo.domain.model;

import com.example.demo.support.PersonMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class PersonModelTest {

    @Test
    void updateConservaElIdYCambiaLosDatos() {
        PersonModel current = PersonMother.anaGuardada();

        PersonModel updated = current.update("Ana Maria", "ana.maria@mail.com", "Nueva123", "77799999");

        assertEquals(current.getId(), updated.getId());
        assertEquals("Ana Maria", updated.getName());
        assertEquals("ana.maria@mail.com", updated.getEmail());
        assertEquals("Nueva123", updated.getPassword());
        assertEquals("77799999", updated.getPhone());
    }

    @Test
    void updateNoModificaElOriginal() {
        PersonModel current = PersonMother.anaGuardada();

        PersonModel updated = current.update("Ana Maria", "ana.maria@mail.com", "Nueva123", "77799999");

        assertNotSame(current, updated);
        assertEquals("Ana", current.getName());
        assertEquals("ana@mail.com", current.getEmail());
    }

    @Test
    void withEncodedPasswordSoloCambiaLaPassword() {
        PersonModel person = PersonMother.ana();

        PersonModel encoded = person.withEncodedPassword("HASH");

        assertEquals("HASH", encoded.getPassword());
        assertEquals(person.getId(), encoded.getId());
        assertEquals(person.getName(), encoded.getName());
        assertEquals(person.getEmail(), encoded.getEmail());
        assertEquals(person.getPhone(), encoded.getPhone());
        assertEquals(PersonMother.PASSWORD, person.getPassword());
    }
}
