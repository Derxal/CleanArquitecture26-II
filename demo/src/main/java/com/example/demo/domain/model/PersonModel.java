package com.example.demo.domain.model;


import com.example.demo.domain.vo.EmailVO;
import com.example.demo.domain.vo.NameVO;
import com.example.demo.domain.vo.PasswordVO;
import com.example.demo.domain.vo.PhoneVO;
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

    public PersonModel(int id, NameVO name, EmailVO email, PasswordVO password, PhoneVO phone) {
        this.id = id;
        this.name = name.getValue();
        this.email = email.getValue();
        this.password = password.getValue();
        this.phone = phone.getValue();
    }
}
