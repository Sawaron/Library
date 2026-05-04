package com.codeandpray.library.exception.entity;

public class FineNotFoundException extends EntityNotFoundException {
    public FineNotFoundException(String message) {
        super(message, "FINE_NOT_FOUND");
    }
}
