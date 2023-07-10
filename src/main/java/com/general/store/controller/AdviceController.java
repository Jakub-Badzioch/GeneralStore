package com.general.store.controller;

import com.general.store.model.dto.ErrorDTO;
import com.general.store.model.dto.FieldErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleBadCredentialsException(BadCredentialsException e) {
        log.warn(e.getMessage());
        return new ErrorDTO("Bad credentials");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorDTO("Data couldn't be found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<FieldErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        return e.getAllErrors().stream()
                .map(error -> new FieldErrorDTO(((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.warn(e.getMessage());
        return new ErrorDTO("Entity already exists");
    }
}