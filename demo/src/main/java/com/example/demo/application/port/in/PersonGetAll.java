package com.example.demo.application.port.in;

import com.example.demo.application.dto.PersonDto;

import java.util.List;

public interface PersonGetAll {

    public List<PersonDto> getAll();
}
