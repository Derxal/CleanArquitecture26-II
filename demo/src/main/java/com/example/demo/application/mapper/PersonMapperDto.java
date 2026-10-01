package com.example.demo.application.mapper;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.domain.model.PersonModel;

public class PersonMapperDto implements PersonMapper<PersonDto>{

    public PersonModel toDomain(PersonDto dto){
        return null; //new PersonModel(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone() );
    }

    public PersonDto toExternal(PersonModel model){
        return new PersonDto(model.getId(), model.getName(), model.getEmail(), model.getPhone());
    }
}
