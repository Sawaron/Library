package com.codeandpray.library.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum BookStatus {
    @Enumerated(EnumType.STRING)
    AVAILABLE,
    UNAVAILABLE
}
