package com.example.demo.application.port.in;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.domain.model.PersonModel;

public interface PersonCreate {

    public PersonDto create(PersonModel personModel);
}
