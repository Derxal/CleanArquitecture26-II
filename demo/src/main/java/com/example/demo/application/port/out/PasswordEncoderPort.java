package com.example.demo.application.port.out;

public interface PasswordEncoderPort {

    public String encode(String rawPassword);
    public boolean matches(String rawPassword, String encodedPassword);
}
