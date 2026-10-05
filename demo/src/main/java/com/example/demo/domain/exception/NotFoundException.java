package com.example.demo.domain.exception;

public class NotFoundException extends DomainException {

    private final String entity;
    private final Object id;

    public NotFoundException(String entity, Object id) {
        super("No se encontro " + entity + " con id " + id);
        this.entity = entity;
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public Object getId() {
        return id;
    }
}
