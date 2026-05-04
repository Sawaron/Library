package com.codeandpray.library.exception.entity;

import com.codeandpray.library.exception.LibraryException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends LibraryException {
    public EntityNotFoundException(String message, String code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }
}
