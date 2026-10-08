package com.example.demo.infrestructure.config;

import com.example.demo.domain.exception.PersonAlreadyExistsException;
import com.example.demo.domain.exception.SessionInvalidCredentialsException;
import com.example.demo.domain.exception.InvalidValueException;
import com.example.demo.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidValueException.class)
    public ProblemDetail handleInvalidValue(InvalidValueException ex, HttpServletRequest request) {
        log.warn("Valor invalido | excepcion={} | status=400 | metodo={} | ruta={} | campo={} | detalle={}",
                ex.getClass().getSimpleName(), request.getMethod(), request.getRequestURI(), ex.getField(), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("field", ex.getField());
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException ex, HttpServletRequest request) {
        log.warn("Recurso no encontrado | excepcion={} | status=404 | metodo={} | ruta={} | entidad={} | id={} | detalle={}",
                ex.getClass().getSimpleName(), request.getMethod(), request.getRequestURI(), ex.getEntity(), ex.getId(), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("entity", ex.getEntity());
        problemDetail.setProperty("id", ex.getId());
        return problemDetail;
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ProblemDetail handleAlreadyExists(PersonAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Recurso duplicado | excepcion={} | status=409 | metodo={} | ruta={} | entidad={} | campo={} | valor={} | detalle={}",
                ex.getClass().getSimpleName(), request.getMethod(), request.getRequestURI(), ex.getEntity(), ex.getField(), ex.getValue(), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setProperty("entity", ex.getEntity());
        problemDetail.setProperty("field", ex.getField());
        return problemDetail;
    }

    @ExceptionHandler(SessionInvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(SessionInvalidCredentialsException ex, HttpServletRequest request) {
        log.warn("Credenciales invalidas | excepcion={} | status=401 | metodo={} | ruta={} | email={} | detalle={}",
                ex.getClass().getSimpleName(), request.getMethod(), request.getRequestURI(), ex.getEmail(), ex.getMessage());

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Restriccion de base de datos | status=409 | metodo={} | ruta={} | detalle={}",
                request.getMethod(), request.getRequestURI(), ex.getMostSpecificCause().getMessage());

        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "El registro entra en conflicto con datos existentes");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        log.warn("Datos invalidos | status=400 | metodo={} | ruta={} | campos={}",
                servletRequest.getMethod(), servletRequest.getRequestURI(), errors);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Datos invalidos");
        problemDetail.setProperty("errors", errors);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Error no controlado | status=500 | metodo={} | ruta={} | excepcion={} | detalle={}",
                request.getMethod(), request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }
}
