package com.codeandpray.library.exception.logic;

public class InvalidAgeCategory extends LogicBadRequestException {
    public InvalidAgeCategory(String message) {
        super(message, "INVALID_AGE_CATEGORY");
    }
}
