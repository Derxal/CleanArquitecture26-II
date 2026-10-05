package com.example.demo.application.services;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.mapper.PersonMapperDto;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.model.PersonModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServices implements PersonGetById, PersonGetAll, PersonCreate {
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
        try{
            return personMapper.toExternal(personRepository.create(personModel));
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

    }
}
