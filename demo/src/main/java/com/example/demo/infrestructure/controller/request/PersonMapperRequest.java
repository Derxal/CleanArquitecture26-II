package com.example.demo.infrestructure.controller.request;

import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.domain.model.PersonModel;

public class PersonMapperRequest implements PersonMapper<PersonDtoRequest> {

    @Override
    public PersonModel toDomain(PersonDtoRequest external) {
        return new PersonModel(
                external.getId(),
                external.getName(),
                external.getEmail(),
                external.getPassword(),
                external.getPhone()
        );
    }

    @Override
    public PersonDtoRequest toExternal(PersonModel model) {
        return new PersonDtoRequest(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPassword(),
                model.getPhone()
        );
    }
}
