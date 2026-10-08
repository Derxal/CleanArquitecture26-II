package com.example.demo.application.port.in;

import com.example.demo.application.dto.LoginDto;

public interface AuthLogin {

    public LoginDto login(String email, String password);
}
