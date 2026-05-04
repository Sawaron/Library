package com.codeandpray.library.exception.logic;

public class LoanAlreadyReturned extends LogicBadRequestException{
    public LoanAlreadyReturned(String message) {
        super(message, "LOAN_ALREADY_RETURNED");
    }
}
