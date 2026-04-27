package com.codeandpray.library.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum LoanStatus {
    @Enumerated(EnumType.STRING)
    ISSUED,
    OVERDUE,
    RETURNED
}