package com.codeandpray.library.enums;

public enum AgeCategory {
    ZERO_PLUS("0+", 0),
    SIX_PLUS("6+", 6),
    TWELVE_PLUS("12+", 12),
    SIXTEEN_PLUS("16+", 16),
    EIGHTEEN_PLUS("18+", 18);

    private final String value;
    private final int minAge;

    AgeCategory(String value, int minAge) {
        this.value = value;
        this.minAge = minAge;
    }

    public String getValue() {
        return value;
    }

    public int getMinAge() {
        return minAge;
    }
}