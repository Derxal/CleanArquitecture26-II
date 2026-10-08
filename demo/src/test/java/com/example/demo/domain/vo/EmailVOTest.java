package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailVOTest {

    @ParameterizedTest
    @ValueSource(strings = {"ana@mail.com", "ana.maria+test@mail.co", "a_b-c@sub.dominio.org"})
    void aceptaEmailsValidos(String email) {
        assertEquals(email, new EmailVO(email).getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaEmailVacio(String email) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new EmailVO(email));
        assertEquals("email", ex.getField());
        assertEquals("El email es obligatorio", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ana", "ana@", "@mail.com", "ana@mail", "ana mail@mail.com"})
    void rechazaFormatosInvalidos(String email) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new EmailVO(email));
        assertEquals("El email no tiene un formato valido", ex.getMessage());
    }

    @Test
    void rechazaMasDeCienCaracteres() {
        String email = "a".repeat(92) + "@mail.com";
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new EmailVO(email));
        assertEquals("email", ex.getField());
    }
}
