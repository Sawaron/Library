package com.codeandpray.library.exception.entity;

public class RatingNotFoundException extends EntityNotFoundException {
    public RatingNotFoundException(String message) {
        super(message, "RATING_NOT_FOUND");
    }
}
