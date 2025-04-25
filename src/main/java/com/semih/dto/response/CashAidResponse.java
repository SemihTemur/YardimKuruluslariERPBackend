package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashAidResponse(BaseResponse baseResponse,
                              String familyName,
                              BigDecimal aidAmount,
                              CurrencyType currency,
                              PeriodType period,
                              Integer duration,
                              BigDecimal totalDonatedAmount,
                              LocalDate startingDate,
                              LocalDate endingDate
) {

}
