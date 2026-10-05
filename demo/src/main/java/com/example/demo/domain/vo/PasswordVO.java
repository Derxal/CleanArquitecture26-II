package com.example.demo.domain.vo;

import com.example.demo.domain.exception.InvalidValueException;

public class PasswordVO {

    private String value;

    private int limitMax = 64;
    private int limitMin = 8;


    public PasswordVO(String value) {

        if(value == null || value.isBlank()){
            throw new InvalidValueException("password","La contraseña es obligatoria");
        }
        if(value.length() < limitMin){
            throw new InvalidValueException("password","La contraseña no puede tener menos de " + limitMin +" caracteres");
        }
        if(value.length() > limitMax){
            throw new InvalidValueException("password","La contraseña no puede tener mas de " + limitMax +" caracteres");
        }
        if(!value.matches(".*[A-Z].*")){
            throw new InvalidValueException("password","La contraseña debe tener al menos una letra mayuscula");
        }
        if(!value.matches(".*[a-z].*")){
            throw new InvalidValueException("password","La contraseña debe tener al menos una letra minuscula");
        }
        if(!value.matches(".*\\d.*")){
            throw new InvalidValueException("password","La contraseña debe tener al menos un numero");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
