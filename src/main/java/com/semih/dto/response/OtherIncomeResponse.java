package com.semih.dto.response;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record OtherIncomeResponse(BaseResponse baseResponse,
                                  String description,
                                  BigDecimal amount,
                                  CurrencyType currency
) {

}
