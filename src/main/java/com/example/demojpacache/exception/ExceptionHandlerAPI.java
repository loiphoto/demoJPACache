package com.example.demojpacache.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAPI {

    @ExceptionHandler({UserNotFoundException.class, EmptyResultDataAccessException.class, RoleNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleAllException(Exception ex){
        return new ErrorMessage(1, ex.getMessage());
    }
}
