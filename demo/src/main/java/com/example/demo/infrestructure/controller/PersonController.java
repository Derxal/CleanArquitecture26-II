package com.example.demo.infrestructure.controller;


import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.infrestructure.controller.request.PersonDtoRequest;
import com.example.demo.infrestructure.controller.request.PersonMapperRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
@Tag(name = "Personas", description = "fdjflajdsalfas")
public class PersonController {
    private final PersonGetAll personGetAll;
    private final PersonGetById personGetById;
    private final PersonCreate personCreate;



    @GetMapping
    @Operation( summary = "Un usuario")
    public PersonDto getById(int id){
        return personGetById.getById(id);
    }


    @GetMapping("/all")
    @Operation( summary = "Get all")
    public List<PersonDto> getAll(){
        return personGetAll.getAll();
    }


    @PostMapping("/")
    @Operation( summary = "Guardar")
    public ResponseEntity<PersonDto> store(@RequestBody PersonDtoRequest request){
        PersonMapper<PersonDtoRequest> mapper = new PersonMapperRequest();
        return ResponseEntity.ok(
                personCreate.create(
                        mapper.toDomain(request)
                )
        );
    }
}
