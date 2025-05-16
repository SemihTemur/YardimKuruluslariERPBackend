package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    SAVE("Ekle"),
    DELETE("Sil"),
    LIST("Görüntüle"),
    UPDATE("Güncelle");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
