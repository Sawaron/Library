package com.codeandpray.library.enums;

public enum AgeCategory {
    ZERO_PLUS("0+"),
    SIX_PLUS("6+"),
    TWELVE_PLUS("12+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+");

    private final String value;

    AgeCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

