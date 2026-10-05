package com.example.demo.application.services;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.mapper.PersonMapperDto;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonDelete;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.application.port.in.PersonUpdate;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.model.PersonModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServices implements PersonGetById, PersonGetAll, PersonCreate, PersonUpdate, PersonDelete {
    private final PersonRepositoryPort personRepository;
    private  final PersonMapper<PersonDto> personMapper;


    public PersonServices(PersonRepositoryPort personRepository) {
        this.personRepository = personRepository;
        this.personMapper = new PersonMapperDto();
    }

    @Override
    public List<PersonDto> getAll() {
        return personRepository.getAll().stream().map(  model -> (PersonDto) personMapper.toExternal(model)   ).toList();
    }

    @Override
    public PersonDto getById(int id) {

        return (PersonDto) personMapper.toExternal(
                personRepository.getById(id)
        );
    }

    @Override
    public PersonDto create(PersonModel personModel) {
        return personMapper.toExternal(personRepository.create(personModel));
    }

    @Override
    public PersonDto update(int id, PersonModel personModel) {
        return personMapper.toExternal(
                personRepository.update(
                        new PersonModel(
                                id,
                                personModel.getName(),
                                personModel.getEmail(),
                                personModel.getPassword(),
                                personModel.getPhone()
                        )
                )
        );
    }

    @Override
    public void delete(int id) {
        personRepository.delete(id);
    }
}
