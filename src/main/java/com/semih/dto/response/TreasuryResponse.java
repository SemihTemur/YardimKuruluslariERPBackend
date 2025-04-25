package com.semih.dto.response;

import java.math.BigDecimal;

public record TreasuryResponse(BaseResponse baseResponse,
                               BigDecimal balance,
                               String currency
) {
    public TreasuryResponse(BaseResponse baseResponse, BigDecimal balance) {
        this(baseResponse, balance, "TRY");
    }
}
