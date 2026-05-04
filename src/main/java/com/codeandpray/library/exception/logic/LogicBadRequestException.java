package com.codeandpray.library.exception.logic;

import com.codeandpray.library.exception.LibraryException;
import org.springframework.http.HttpStatus;

public class LogicBadRequestException extends LibraryException {
    public LogicBadRequestException(String message, String code) {
        super(message, code, HttpStatus.BAD_REQUEST);
    }
}
