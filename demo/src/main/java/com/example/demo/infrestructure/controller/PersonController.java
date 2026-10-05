package com.example.demo.infrestructure.controller;


import com.example.demo.application.dto.PersonDto;
import com.example.demo.application.mapper.PersonMapper;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonDelete;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.application.port.in.PersonUpdate;
import com.example.demo.infrestructure.controller.request.PersonDtoRequest;
import com.example.demo.infrestructure.controller.request.PersonMapperRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final PersonUpdate personUpdate;
    private final PersonDelete personDelete;



    @GetMapping("/{id}")
    @Operation( summary = "Un usuario")
    public PersonDto getById(@PathVariable int id){
        return personGetById.getById(id);
    }


    @GetMapping("/all")
    @Operation( summary = "Get all")
    public List<PersonDto> getAll(){
        return personGetAll.getAll();
    }


    @PostMapping
    @Operation( summary = "Guardar")
    public ResponseEntity<PersonDto> store(@Valid @RequestBody PersonDtoRequest request){
        PersonMapper<PersonDtoRequest> mapper = new PersonMapperRequest();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                personCreate.create(
                        mapper.toDomain(request)
                )
        );
    }


    @PutMapping("/{id}")
    @Operation( summary = "Actualizar")
    public ResponseEntity<PersonDto> update(@PathVariable int id, @Valid @RequestBody PersonDtoRequest request){
        PersonMapper<PersonDtoRequest> mapper = new PersonMapperRequest();
        return ResponseEntity.ok(
                personUpdate.update(
                        id,
                        mapper.toDomain(request)
                )
        );
    }


    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar")
    public ResponseEntity<Void> delete(@PathVariable int id){
        personDelete.delete(id);
        return ResponseEntity.noContent().build();
    }
}
