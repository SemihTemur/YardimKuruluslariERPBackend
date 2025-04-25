package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;

import java.math.BigDecimal;

public record CashAidRequest(String familyName,
                             BigDecimal aidAmount,
                             CurrencyType currency,
                             PeriodType period,
                             Integer duration
) {

}
