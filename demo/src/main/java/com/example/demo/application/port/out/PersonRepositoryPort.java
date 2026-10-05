package com.example.demo.application.port.out;

import com.example.demo.domain.model.PersonModel;

import java.util.List;

public interface PersonRepositoryPort {

    public PersonModel getById(int id);
    public List<PersonModel> getAll();
    public PersonModel create(PersonModel personModel);


}
