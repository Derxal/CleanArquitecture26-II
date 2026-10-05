package com.example.demo.domain.vo;


public class PhoneVO {

    private String value;

    private int limitMax = 15;
    private int limitMin = 7;


    public PhoneVO(String value) {

        if(value == null || value.isBlank()){
            throw new RuntimeException("El telefono es obligatorio");
        }
        if(!value.matches("^\\+?\\d+$")){
            throw new RuntimeException("El telefono solo puede contener numeros");
        }
        if(value.replace("+", "").length() < limitMin){
            throw new RuntimeException("El telefono no puede tener menos de " + limitMin +" digitos");
        }
        if(value.replace("+", "").length() > limitMax){
            throw new RuntimeException("El telefono no puede tener mas de " + limitMax +" digitos");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
