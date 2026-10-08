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
    private final PersonMapper<PersonDtoRequest> mapperRequest = new PersonMapperRequest();



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
        return ResponseEntity.status(HttpStatus.CREATED).body(
                personCreate.create(
                        mapperRequest.toDomain(request)
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable int id, @Valid @RequestBody PersonDtoRequest request){
        return ResponseEntity.ok(
                personUpdate.update(
                        id,
                        mapperRequest.toDomain(request)
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        personDelete.delete(id);
        return ResponseEntity.noContent().build();
    }
}
