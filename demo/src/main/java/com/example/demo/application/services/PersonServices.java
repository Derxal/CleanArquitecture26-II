package com.example.demo.application.services;

import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.mapper.PersonMapperDto;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonDelete;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.application.port.in.PersonUpdate;
import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.domain.exception.PersonAlreadyExistsException;
import com.example.demo.domain.model.PersonModel;

import java.util.List;


public class PersonServices implements PersonGetById, PersonGetAll, PersonCreate, PersonUpdate, PersonDelete {
    private final PersonRepositoryPort personRepository;
    private final PasswordEncoderPort passwordEncoder;
    private  final PersonMapper<PersonDto> personMapper;


    public PersonServices(PersonRepositoryPort personRepository, PasswordEncoderPort passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
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
    public PersonDto create(PersonModel person) {
     
        if(personRepository.existsByEmail(person.getEmail())){
            throw new PersonAlreadyExistsException("email", person.getEmail());
        }

        return personMapper.toExternal(
                personRepository.create(
                        person.withEncodedPassword(passwordEncoder.encode(person.getPassword()))
                )
        );
    }

    @Override
    public PersonDto update(int id, PersonModel personModel) {
        PersonModel current = personRepository.getById(id);
        PersonModel person = current.update(
                personModel.getName(),
                personModel.getEmail(),
                personModel.getPassword(),
                personModel.getPhone()
        );

        boolean emailChanged = !current.getEmail().equalsIgnoreCase(person.getEmail());

        if(emailChanged && personRepository.existsByEmail(person.getEmail())){
            throw new PersonAlreadyExistsException("email", person.getEmail());
        }

        return personMapper.toExternal(
                personRepository.update(
                        person.withEncodedPassword(passwordEncoder.encode(person.getPassword()))
                )
        );
    }

    @Override
    public void delete(int id) {
        personRepository.delete(id);
    }
}
