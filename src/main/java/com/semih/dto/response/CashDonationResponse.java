package com.semih.dto.response;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record CashDonationResponse(BaseResponse baseResponse,
                                   String donorFirstName,
                                   String donorLastName,
                                   BigDecimal amount,
                                   CurrencyType currency
) {

}
