package com.codeandpray.library.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ErrorResponse> handleLibraryException(LibraryException ex){
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, ex.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEverything(Exception ex){
        ErrorResponse error = new ErrorResponse();
        error.setCode("INTERNAL_SERVER_ERROR");
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
