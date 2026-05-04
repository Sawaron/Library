package com.codeandpray.library.exception.entity;

public class ReservationNotFoundException extends EntityNotFoundException {
    public ReservationNotFoundException(String message) {
        super(message, "RESERVATION_NOT_FOUND");
    }
}
