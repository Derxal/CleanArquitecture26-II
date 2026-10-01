package com.example.demo.infrestructure.adapter.repositories;

import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.domain.model.PersonModel;

public class PersonMapperEntity implements PersonMapper<PersonEntity> {
    @Override
    public PersonModel toDomain(PersonEntity external) {
        //return new PersonModel(external.getId(), external.getName(), external.getEmail(), ex);
        return new PersonModel(
                external.getId(),
                external.getName(),
                external.getEmail(),
                external.getPassword(),
                external.getPhone()
        );
    }

    @Override
    public PersonEntity toExternal(PersonModel model) {
        return new PersonEntity(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPassword(),
                model.getPhone()
        );
    }
}
