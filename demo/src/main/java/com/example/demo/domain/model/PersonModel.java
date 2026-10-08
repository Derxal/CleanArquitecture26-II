package com.example.demo.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class PersonModel {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public PersonModel(int id, String name, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public PersonModel update(String name, String email, String password, String phone) {
        return new PersonModel(this.id, name, email, password, phone);
    }

    public PersonModel withEncodedPassword(String encodedPassword) {
        return new PersonModel(this.id, this.name, this.email, encodedPassword, this.phone);
    }
}
