package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {

    AILE("Aile"),
    OGRENCI("Öğrenci"),
    BAGISCI("Bağışçı");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    @JsonValue
    public String getUserType() {
        return userType;
    }
}
