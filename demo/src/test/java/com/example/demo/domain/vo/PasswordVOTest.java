package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordVOTest {

    @Test
    void aceptaUnaPasswordValida() {
        assertEquals("Clave123", new PasswordVO("Clave123").getValue());
    }

    @Test
    void aceptaLosLimitesExactos() {
        assertEquals(8, new PasswordVO("Abcdef12").getValue().length());
        assertEquals(64, new PasswordVO("Ab1" + "x".repeat(61)).getValue().length());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaPasswordVacia(String password) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PasswordVO(password));
        assertEquals("password", ex.getField());
        assertEquals("La contraseña es obligatoria", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Abc1234,          La contraseña no puede tener menos de 8 caracteres",
            "clave1234,        La contraseña debe tener al menos una letra mayuscula",
            "CLAVE1234,        La contraseña debe tener al menos una letra minuscula",
            "ClaveSinNumero,   La contraseña debe tener al menos un numero"
    })
    void rechazaPasswordsQueNoCumplenLasReglas(String password, String expectedMessage) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PasswordVO(password));
        assertEquals("password", ex.getField());
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void rechazaMasDeSesentaYCuatroCaracteres() {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PasswordVO("Ab1" + "x".repeat(62)));
        assertEquals("La contraseña no puede tener mas de 64 caracteres", ex.getMessage());
    }
}
