package com.codeandpray.library.exception.entity;

public class ReviewNotFoundException extends EntityNotFoundException{
    public ReviewNotFoundException(String message) {
        super(message, "REVIEW_NOT_FOUND");
    }
}
