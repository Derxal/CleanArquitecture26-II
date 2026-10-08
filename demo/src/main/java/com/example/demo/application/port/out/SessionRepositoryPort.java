package com.example.demo.application.port.out;

import com.example.demo.domain.model.SessionModel;

public interface SessionRepositoryPort {

    public SessionModel create(SessionModel sessionModel);
    public void deleteByToken(String token);
}
