package com.semih.dto.response;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record CashAidExpenseResponse(BaseResponse baseResponse,
                                     String familyName,
                                     BigDecimal totalAidAmount,
                                     CurrencyType currencyType
) {

}
