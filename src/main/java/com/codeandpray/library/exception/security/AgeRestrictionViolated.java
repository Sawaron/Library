package com.codeandpray.library.exception.security;

import com.codeandpray.library.exception.LibraryException;
import org.springframework.http.HttpStatus;

public class AgeRestrictionViolated extends LibraryException {
    public AgeRestrictionViolated(String message) {
        super(message, "AGE_RESTRICTION_VIOLATED", HttpStatus.FORBIDDEN);
    }
}
