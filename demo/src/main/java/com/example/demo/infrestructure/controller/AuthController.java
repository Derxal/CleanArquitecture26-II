package com.example.demo.infrestructure.controller;


import com.example.demo.application.dto.LoginDto;
import com.example.demo.application.port.in.AuthLogin;
import com.example.demo.application.port.in.AuthLogout;
import com.example.demo.infrestructure.controller.request.LoginRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthLogin authLogin;
    private final AuthLogout authLogout;



    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(
                authLogin.login(request.getEmail(), request.getPassword())
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        authLogout.logout(authorization.replaceFirst("(?i)^Bearer\\s+", ""));
        return ResponseEntity.noContent().build();
    }
}
