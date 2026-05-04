package com.codeandpray.library.exception.entity;


public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(String message) {
        super(message, "BOOK_NOT_FOUND");
    }
}
