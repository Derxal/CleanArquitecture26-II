package com.example.demo.domain.vo;


public class NameVO {

    private String value;

    private int limitMax = 50;
    private int limitMin = 2;


    public NameVO(String value) {

        if(value.length() < limitMin){
            throw new RuntimeException("El nombre no puede tener menos de " + limitMin +" caracteres");
        }
        if(value.length() > limitMax){
            throw new RuntimeException("El nombre no puede tener mas de " + limitMax +" caracteres");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
