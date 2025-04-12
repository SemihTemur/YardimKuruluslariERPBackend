package com.semih.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TreasuryResponse extends BaseResponse {

    private BigDecimal balance;

    private final String currency = "TRY";

    public TreasuryResponse(BigDecimal balance) {
        this.balance = balance;
    }
}
