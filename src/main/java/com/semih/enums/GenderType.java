package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum 
GenderType {

    ERKEK("Erkek"),       // Erkek
    KADIN("Kadın");     // Kadın

    private final String value;

    GenderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
