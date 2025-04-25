package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;

import java.math.BigDecimal;

public record ScholarshipRequest(String studentName,
                                 String studentSurname,
                                 BigDecimal scholarshipAmount,
                                 CurrencyType currency,
                                 PeriodType period,
                                 Integer duration) {
    
}
