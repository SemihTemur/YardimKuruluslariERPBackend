package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CashAidResponse {

    private String familyName;

    private BigDecimal aidAmount;

    private CurrencyType currency;

    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration;

    private LocalDate startingDate;

    private LocalDate endingDate;

}
