package com.example.demo.infrestructure.adapter.repositories;

import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.domain.vo.EmailVO;
import com.example.demo.domain.vo.NameVO;
import com.example.demo.domain.vo.PasswordVO;
import com.example.demo.domain.vo.PhoneVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
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
        PersonEntity entity = mapperEntity.toExternal(
                new PersonModel(
                        personModel.getId(),
                        new NameVO(personModel.getName()),
                        new EmailVO(personModel.getEmail()),
                        new PasswordVO(personModel.getPassword()),
                        new PhoneVO(personModel.getPhone())
                )
        );

        return mapperEntity.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public PersonModel update(PersonModel personModel) {
        findEntity(personModel.getId());

        PersonEntity entity = mapperEntity.toExternal(
                new PersonModel(
                        personModel.getId(),
                        new NameVO(personModel.getName()),
                        new EmailVO(personModel.getEmail()),
                        new PasswordVO(personModel.getPassword()),
                        new PhoneVO(personModel.getPhone())
                )
        );

        return mapperEntity.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public void delete(int id) {
        repositoryJpa.delete(
                findEntity(id)
        );
    }

    private PersonEntity findEntity(int id) {
        return repositoryJpa.findById(id)
                .orElseThrow(() -> new NotFoundException("persona", id));
    }

}
