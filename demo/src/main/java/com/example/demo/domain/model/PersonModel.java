package com.example.demo.domain.model;


import com.example.demo.domain.vo.NameVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PersonModel {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;


}
