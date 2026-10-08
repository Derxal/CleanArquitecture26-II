package com.example.demo.application.services;

import com.example.demo.application.dto.LoginDto;
import com.example.demo.application.port.in.AuthLogin;
import com.example.demo.application.port.in.AuthLogout;
import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.application.port.out.SessionRepositoryPort;
import com.example.demo.application.port.out.TokenGeneratorPort;
import com.example.demo.domain.exception.SessionInvalidCredentialsException;
import com.example.demo.domain.model.PersonModel;
import com.example.demo.domain.model.SessionModel;


public class AuthServices implements AuthLogin, AuthLogout {
    private final PersonRepositoryPort personRepository;
    private final SessionRepositoryPort sessionRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGeneratorPort tokenGenerator;


    public AuthServices(PersonRepositoryPort personRepository, SessionRepositoryPort sessionRepository,
                        PasswordEncoderPort passwordEncoder, TokenGeneratorPort tokenGenerator) {
        this.personRepository = personRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public LoginDto login(String email, String password) {
        PersonModel person = personRepository.findByEmail(email)
                .orElseThrow(() -> new SessionInvalidCredentialsException(email));

        if(!passwordEncoder.matches(password, person.getPassword())){
            throw new SessionInvalidCredentialsException(email);
        }

        SessionModel session = sessionRepository.create(
                SessionModel.start(tokenGenerator.generate(), person.getId())
        );

        return new LoginDto(session.getToken(), person.getId(), person.getName(), person.getEmail());
    }

    @Override
    public void logout(String token) {
        sessionRepository.deleteByToken(token);
    }
}
