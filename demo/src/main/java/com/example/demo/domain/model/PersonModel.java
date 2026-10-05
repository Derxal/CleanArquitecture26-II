package com.example.demo.domain.model;


import com.example.demo.domain.vo.NameVO;
import lombok.AllArgsConstructor;
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

    public PersonModel(int id, NameVO name, String email, String password, String phone) {
        this.name = name.getValue();
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
