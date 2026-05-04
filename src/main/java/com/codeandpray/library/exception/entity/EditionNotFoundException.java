package com.codeandpray.library.exception.entity;

public class EditionNotFoundException extends EntityNotFoundException {
    public EditionNotFoundException(String message) {
        super(message, "EDITION_NOT_FOUND");
    }
}
