package com.semih.dto.request;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record OtherIncomeRequest(String description,
                                 BigDecimal amount,
                                 CurrencyType currency
) {
    
}
