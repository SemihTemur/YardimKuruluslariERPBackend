package com.semih.dto.response;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record OtherExpenseResponse(BaseResponse baseResponse,
                                   String description,
                                   BigDecimal amount,
                                   CurrencyType currency
) {

}
