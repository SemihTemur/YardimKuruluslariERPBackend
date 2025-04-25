package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ScholarshipResponse(BaseResponse baseResponse,
                                  String studentName,
                                  String studentSurname,
                                  BigDecimal scholarshipAmount,
                                  CurrencyType currency,
                                  PeriodType period,
                                  Integer duration,
                                  LocalDate startingDate,
                                  LocalDate endingDate,
                                  BigDecimal totalDonatedAmount
) {
}
