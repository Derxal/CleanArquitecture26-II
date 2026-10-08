package com.example.demo.support;

import com.example.demo.domain.model.PersonModel;

public class PersonMother {

    public static final String PASSWORD = "Clave123";
    public static final String HASH = "$2a$10$hashDePrueba";

    public static PersonModel ana() {
        return new PersonModel(0, "Ana", "ana@mail.com", PASSWORD, "77712345");
    }

    public static PersonModel anaGuardada() {
        return new PersonModel(1, "Ana", "ana@mail.com", HASH, "77712345");
    }

    public static PersonModel luis() {
        return new PersonModel(0, "Luis", "luis@mail.com", "Clave456", "77712346");
    }
}
