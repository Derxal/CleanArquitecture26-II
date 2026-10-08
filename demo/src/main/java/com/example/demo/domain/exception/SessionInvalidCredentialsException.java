package com.example.demo.domain.exception;

public class SessionInvalidCredentialsException extends DomainException {

    private final String email;

    public SessionInvalidCredentialsException(String email) {
        super("Email o contraseña incorrectos");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
