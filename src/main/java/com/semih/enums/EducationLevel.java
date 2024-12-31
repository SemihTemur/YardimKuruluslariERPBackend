package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EducationLevel {

    LISE("Lise"),        // Lise
    LISANS("Lisans"),        // Lisans
    YUKSEK_LISANS("Yüksek Lisans"),   // Yüksek Lisans
    DOKTORA("Doktora");       // Doktora

    private final String value;

    EducationLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
