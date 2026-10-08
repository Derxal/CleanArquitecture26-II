package com.example.demo.infrestructure.adapter.repositories.session;

import com.example.demo.domain.model.SessionModel;
import com.example.demo.infrestructure.adapter.repositories.person.PersonEntity;

public class SessionMapperEntity {

    public SessionModel toDomain(SessionEntity external) {
        return new SessionModel(
                external.getId(),
                external.getToken(),
                external.getPerson().getId(),
                external.getCreatedAt()
        );
    }

    public SessionEntity toExternal(SessionModel model, PersonEntity person) {
        return new SessionEntity(
                model.getId(),
                model.getToken(),
                person,
                model.getCreatedAt()
        );
    }
}
