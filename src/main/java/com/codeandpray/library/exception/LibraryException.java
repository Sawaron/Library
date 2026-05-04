package com.codeandpray.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public LibraryException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
