package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;

public class PhoneVO {

    private String value;

    private int limitMax = 15;
    private int limitMin = 7;


    public PhoneVO(String value) {

        if(value == null || value.isBlank()){
            throw new InvalidValueException("phone","El telefono es obligatorio");
        }
        if(!value.matches("^\\+?\\d+$")){
            throw new InvalidValueException("phone","El telefono solo puede contener numeros");
        }
        if(value.replace("+", "").length() < limitMin){
            throw new InvalidValueException("phone","El telefono no puede tener menos de " + limitMin +" digitos");
        }
        if(value.replace("+", "").length() > limitMax){
            throw new InvalidValueException("phone","El telefono no puede tener mas de " + limitMax +" digitos");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
