package com.example.demo.infrestructure.adapter.repositories.session;

import com.example.demo.application.port.out.SessionRepositoryPort;
import com.example.demo.domain.model.SessionModel;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryJpa;


public class SessionRepositoryAdapter implements SessionRepositoryPort {
    private final SessionRepositoryJpa repositoryJpa;
    private final PersonRepositoryJpa personRepositoryJpa;
    private final SessionMapperEntity mapperEntity;

    public SessionRepositoryAdapter(SessionRepositoryJpa repositoryJpa, PersonRepositoryJpa personRepositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.personRepositoryJpa = personRepositoryJpa;
        this.mapperEntity = new SessionMapperEntity();
    }

    @Override
    public SessionModel create(SessionModel sessionModel) {
        return mapperEntity.toDomain(
                repositoryJpa.save(
                        mapperEntity.toExternal(
                                sessionModel,
                                personRepositoryJpa.getReferenceById(sessionModel.getPersonId())
                        )
                )
        );
    }

    @Override
    public void deleteByToken(String token) {
        repositoryJpa.deleteByToken(token);
    }
}
