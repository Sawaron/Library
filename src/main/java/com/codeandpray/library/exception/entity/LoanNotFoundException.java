package com.codeandpray.library.exception.entity;

public class LoanNotFoundException extends EntityNotFoundException {
    public LoanNotFoundException(String message) {
        super(message, "LOAN_NOT_FOUND");
    }
}
