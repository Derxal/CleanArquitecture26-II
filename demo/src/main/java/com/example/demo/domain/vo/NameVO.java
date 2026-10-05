package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;

public class NameVO {

    private String value;

    private int limitMax = 50;
    private int limitMin = 2;


    public NameVO(String value) {

        if(value == null || value.isBlank()){
            throw new InvalidValueException("name","El nombre es obligatorio");
        }
        if(value.length() < limitMin){
            throw new InvalidValueException("name","El nombre no puede tener menos de " + limitMin +" caracteres");
        }
        if(value.length() > limitMax){
            throw new InvalidValueException("name","El nombre no puede tener mas de " + limitMax +" caracteres");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
