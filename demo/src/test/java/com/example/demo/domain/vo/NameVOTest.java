package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameVOTest {

    @Test
    void aceptaUnNombreValido() {
        assertEquals("Ana", new NameVO("Ana").getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaNombreVacio(String name) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new NameVO(name));
        assertEquals("name", ex.getField());
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void aceptaLosLimitesExactos() {
        assertEquals("Al", new NameVO("Al").getValue());
        assertEquals(50, new NameVO("a".repeat(50)).getValue().length());
    }

    @Test
    void rechazaMenosDeDosCaracteres() {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new NameVO("A"));
        assertEquals("name", ex.getField());
    }

    @Test
    void rechazaMasDeCincuentaCaracteres() {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new NameVO("a".repeat(51)));
        assertEquals("name", ex.getField());
    }
}
