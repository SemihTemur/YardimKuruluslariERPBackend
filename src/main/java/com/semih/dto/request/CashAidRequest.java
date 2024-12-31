package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CashAidRequest {

    private String familyName;

    private BigDecimal aidAmount;

    private CurrencyType currency;

    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration;

}
