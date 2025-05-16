package com.semih.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityName {
    STUDENT("Öğrenci"),
    SCHOLARSHIP("Burs"),
    OTHERINCOME("Diğer Gelirler"),
    OTHEREXPENSE("Diğer Giderler"),
    LOG("Log"),
    INVENTORY("Envanter"),
    INKINDDONATION("Ayni Bağış"),
    INKINDAID("Ayni Yardım"),
    FAMILY("Aile"),
    DONOR("Bağışçı"),
    CUSTOMUSER("Kullanıcı"),
    ROLE("Rol"),
    AUTHORIZATION("Yetkilendirme"),
    CATEGORY("Kategori"),
    CASHDONATION("Nakdi Bağış"),
    CASHAID("Nakdi Yardım"),
    TREASURY("Kasa");


    private final String value;

    EntityName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

