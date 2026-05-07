package com.codeandpray.library.exception.entity;


public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
}
