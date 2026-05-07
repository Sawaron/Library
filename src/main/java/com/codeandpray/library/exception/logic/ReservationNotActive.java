package com.codeandpray.library.exception.logic;

public class ReservationNotActive extends LogicBadRequestException {
    public ReservationNotActive(String message) {
        super(message, "RESERVATION_NOT_ACTIVE");
    }
}
