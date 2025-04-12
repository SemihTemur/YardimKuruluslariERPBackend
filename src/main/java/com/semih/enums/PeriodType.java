package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PeriodType {

    HAFTALIK("Haftalık"),    // Haftalık
    AYLIK("Aylık"),   // Aylık
    YILLIK("Yıllık"),    // Yıllık
    TEK_SEFERLİK("Tek Seferlik");

    private final String value;

    PeriodType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
