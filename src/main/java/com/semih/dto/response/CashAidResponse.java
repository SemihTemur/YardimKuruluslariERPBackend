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
public class CashAidResponse extends BaseResponse {

    private String familyName;

    private BigDecimal aidAmount;

    private CurrencyType currency;

    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration;

    private BigDecimal totalDonatedAmount;

    private LocalDate startingDate;

    private LocalDate endingDate;

    public CashAidResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String familyName, BigDecimal aidAmount, CurrencyType currency, PeriodType period, Integer duration, BigDecimal totalDonatedAmount, LocalDate startingDate, LocalDate endingDate) {
        super(id, createdDate, modifiedDate);
        this.familyName = familyName;
        this.aidAmount = aidAmount;
        this.currency = currency;
        this.period = period;
        this.duration = duration;
        this.totalDonatedAmount = totalDonatedAmount;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }
}
