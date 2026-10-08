package com.example.demo.domain.exception;

public class PersonAlreadyExistsException extends DomainException {

    private final String entity = "persona";
    private final String field;
    private final Object value;

    public PersonAlreadyExistsException(String field, Object value) {
        super("Ya existe persona con " + field + " " + value);
        this.field = field;
        this.value = value;
    }

    public String getEntity() {
        return entity;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
