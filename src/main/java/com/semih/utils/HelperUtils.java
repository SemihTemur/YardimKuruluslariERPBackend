package com.semih.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class HelperUtils {

    // Bu metot toplam bagıslanan parayı hesaplar
    public BigDecimal calculateTotalDonatedAmount(BigDecimal amount, Integer duration) {
        return amount.multiply(BigDecimal.valueOf(duration));
    }

    public LocalDate determineEndingDate(String periodType, Integer duration, LocalDate startingDate) {
        return switch (periodType) {
            case "HAFTALIK" -> startingDate.plusWeeks(duration);
            case "AYLIK" -> startingDate.plusMonths(duration);
            case "YILLIK" -> startingDate.plusYears(duration);
            default -> startingDate;
        };
    }

    public Integer calculateTotalDonatedQuantity(Integer quantity, Integer duration) {
        return quantity * duration;
    }

}
