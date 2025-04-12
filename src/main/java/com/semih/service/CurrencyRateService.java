package com.semih.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyRateService {

    public BigDecimal convertToTry(String currencyType, BigDecimal scholarshipAmount) {
        return switch (currencyType) {
            case "USD", "EUR" -> scholarshipAmount;
            default -> scholarshipAmount;
        };
    }

}
