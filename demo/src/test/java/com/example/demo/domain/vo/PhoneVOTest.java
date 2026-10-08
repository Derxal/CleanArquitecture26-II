package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneVOTest {

    @ParameterizedTest
    @ValueSource(strings = {"77712345", "+59177712345", "1234567", "123456789012345"})
    void aceptaTelefonosValidos(String phone) {
        assertEquals(phone, new PhoneVO(phone).getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rechazaTelefonoVacio(String phone) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PhoneVO(phone));
        assertEquals("phone", ex.getField());
        assertEquals("El telefono es obligatorio", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "777-1234", "777 12345", "77712345+"})
    void rechazaCaracteresQueNoSonNumeros(String phone) {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PhoneVO(phone));
        assertEquals("El telefono solo puede contener numeros", ex.getMessage());
    }

    @Test
    void rechazaMenosDeSieteDigitos() {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PhoneVO("123456"));
        assertEquals("El telefono no puede tener menos de 7 digitos", ex.getMessage());
    }

    @Test
    void rechazaMasDeQuinceDigitos() {
        InvalidValueException ex = assertThrows(InvalidValueException.class, () -> new PhoneVO("1234567890123456"));
        assertEquals("El telefono no puede tener mas de 15 digitos", ex.getMessage());
    }

    @Test
    void elSignoMasNoCuentaComoDigito() {
        assertThrows(InvalidValueException.class, () -> new PhoneVO("+123456"));
    }
}
