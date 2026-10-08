package com.example.demo.application.port.out;

import com.example.demo.domain.model.PersonModel;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryPort {

    public PersonModel getById(int id);
    public List<PersonModel> getAll();
    public PersonModel create(PersonModel personModel);
    public PersonModel update(PersonModel personModel);
    public void delete(int id);
    public boolean existsByEmail(String email);
    public Optional<PersonModel> findByEmail(String email);


}
