package com.semih.dto.response;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record ScholarshipExpenseResponse(BaseResponse baseResponse,
                                         String studentName,
                                         String studentSurname,
                                         BigDecimal scholarshipExpense,
                                         CurrencyType currencyType
) {
    
}
