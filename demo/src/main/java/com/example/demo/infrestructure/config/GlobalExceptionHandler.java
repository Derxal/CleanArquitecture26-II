package com.example.demo.infrestructure.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage() // En producción puedes cambiar esto por un mensaje genérico por seguridad
        );
        //problemDetail.setTitle("Error Interno del Servidor");
        //problemDetail.setType(URI.create("https://api.tusitio.com/errors/internal-server-error"));

        return problemDetail;
    }
}