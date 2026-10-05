package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;

public class EmailVO {

    private String value;

    private int limitMax = 100;
    private String pattern = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";


    public EmailVO(String value) {

        if(value == null || value.isBlank()){
            throw new InvalidValueException("email","El email es obligatorio");
        }
        if(value.length() > limitMax){
            throw new InvalidValueException("email","El email no puede tener mas de " + limitMax +" caracteres");
        }
        if(!value.matches(pattern)){
            throw new InvalidValueException("email","El email no tiene un formato valido");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
