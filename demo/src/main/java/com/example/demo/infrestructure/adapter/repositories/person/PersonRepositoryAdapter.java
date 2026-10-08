package com.example.demo.infrestructure.adapter.repositories.person;

import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.domain.vo.EmailVO;
import com.example.demo.domain.vo.NameVO;
import com.example.demo.domain.vo.PasswordVO;
import com.example.demo.domain.vo.PhoneVO;

import java.util.List;
import java.util.Optional;


public class PersonRepositoryAdapter implements PersonRepositoryPort {
    private final PersonRepositoryJpa repositoryJpa;
    private final PersonMapper<PersonEntity> mapperEntity;

    public PersonRepositoryAdapter(PersonRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.mapperEntity = new PersonMapperEntity();
    }

    @Override
    public PersonModel getById(int id) {
        return mapperEntity.toDomain(
                findEntity(id)
        );
    }

    @Override
    public List<PersonModel> getAll() {
        return repositoryJpa.findAll().stream().map( entity ->  mapperEntity.toDomain( entity ) ). toList();
    }

    @Override
    public PersonModel create(PersonModel personModel) {
        return mapperEntity.toDomain(
                repositoryJpa.save(mapperEntity.toExternal(validate(personModel)))
        );
    }

    @Override
    public PersonModel update(PersonModel personModel) {
        findEntity(personModel.getId());

        return mapperEntity.toDomain(
                repositoryJpa.save(mapperEntity.toExternal(validate(personModel)))
        );
    }

    @Override
    public void delete(int id) {
        repositoryJpa.delete(
                findEntity(id)
        );
    }

    @Override
    public boolean existsByEmail(String email) {
        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public Optional<PersonModel> findByEmail(String email) {
        return repositoryJpa.findByEmail(email).map( entity -> mapperEntity.toDomain( entity ) );
    }

    private PersonModel validate(PersonModel personModel) {
        return new PersonModel(
                personModel.getId(),
                new NameVO(personModel.getName()).getValue(),
                new EmailVO(personModel.getEmail()).getValue(),
                new PasswordVO(personModel.getPassword()).getValue(),
                new PhoneVO(personModel.getPhone()).getValue()
        );
    }

    private PersonEntity findEntity(int id) {
        return repositoryJpa.findById(id)
                .orElseThrow(() -> new NotFoundException("persona", id));
    }

}
