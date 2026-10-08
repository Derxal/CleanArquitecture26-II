package com.example.demo.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class SessionModel {

    private int id;
    private String token;
    private int personId;
    private LocalDateTime createdAt;

    public SessionModel(int id, String token, int personId, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.personId = personId;
        this.createdAt = createdAt;
    }

    public static SessionModel start(String token, int personId) {
        return new SessionModel(0, token, personId, LocalDateTime.now());
    }
}
