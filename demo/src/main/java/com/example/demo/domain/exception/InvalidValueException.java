package com.example.demo.domain.exception;

public class InvalidValueException extends DomainException {

    private final String field;

    public InvalidValueException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
