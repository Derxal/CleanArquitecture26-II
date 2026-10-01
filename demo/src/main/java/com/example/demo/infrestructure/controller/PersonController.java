package com.example.demo.infrestructure.controller;


import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonGetById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
@Tag(name = "Personas", description = "fdjflajdsalfas")
public class PersonController {
    private final PersonGetAll personGetAll;
    private final PersonGetById personGetById;



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
}
