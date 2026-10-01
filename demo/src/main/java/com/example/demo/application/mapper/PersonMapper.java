package com.example.demo.application.mapper;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.domain.model.PersonModel;

public interface PersonMapper<T> {

    public PersonModel toDomain(T external);

    public T toExternal(PersonModel model);


}
