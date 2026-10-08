package com.example.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionModelTest {

    @Test
    void startCreaUnaSesionNueva() {
        LocalDateTime before = LocalDateTime.now();

        SessionModel session = SessionModel.start("token-123", 7);

        assertEquals(0, session.getId());
        assertEquals("token-123", session.getToken());
        assertEquals(7, session.getPersonId());
        assertFalse(session.getCreatedAt().isBefore(before));
        assertTrue(session.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
