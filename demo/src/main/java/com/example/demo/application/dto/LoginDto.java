package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {

    public String token;
    public int personId;
    public String name;
    public String email;

}
