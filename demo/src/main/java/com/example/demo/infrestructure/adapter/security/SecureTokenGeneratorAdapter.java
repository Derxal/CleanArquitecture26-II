package com.example.demo.infrestructure.adapter.security;

import com.example.demo.application.port.out.TokenGeneratorPort;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureTokenGeneratorAdapter implements TokenGeneratorPort {
    private final SecureRandom random;

    public SecureTokenGeneratorAdapter() {
        this.random = new SecureRandom();
    }

    @Override
    public String generate() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
