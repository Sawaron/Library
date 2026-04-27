package com.codeandpray.library.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum FineStatus {
    @Enumerated(EnumType.STRING)
    PAID, UNPAID
}