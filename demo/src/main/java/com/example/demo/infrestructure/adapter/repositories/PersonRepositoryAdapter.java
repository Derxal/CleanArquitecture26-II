package com.example.demo.infrestructure.adapter.repositories;

import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.domain.vo.NameVO;
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
                repositoryJpa.findById(id).get()
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
                        personModel.getEmail(),
                        personModel.getPassword(),
                        personModel.getPhone()
                )
        );

        return mapperEntity.toDomain(repositoryJpa.save(entity));
    }

}
