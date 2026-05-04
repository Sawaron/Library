package com.codeandpray.library.exception.logic;

public class ReviewAlreadyExists extends LogicBadRequestException {
    public ReviewAlreadyExists(String message) {
        super(message, "REVIEW_ALREADY_EXISTS");
    }
}
