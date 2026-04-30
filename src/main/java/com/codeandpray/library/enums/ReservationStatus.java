package com.codeandpray.library.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum ReservationStatus {
    @Enumerated(EnumType.STRING)
    ACTIVE,
    FULFILLED,
    CANCELLED,
    EXPIRED,
}
