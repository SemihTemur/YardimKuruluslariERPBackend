package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ScholarshipResponse extends BaseResponse {

    private String studentName;

    private String studentSurname;

    private BigDecimal scholarshipAmount;

    private CurrencyType currency;

    private PeriodType period;

    private Integer duration;

    private LocalDate startingDate;

    private LocalDate endingDate;

    private BigDecimal totalDonatedAmount;

    public ScholarshipResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String studentName, String studentSurname, BigDecimal scholarshipAmount, CurrencyType currency, PeriodType period, Integer duration, LocalDate startingDate, LocalDate endingDate, BigDecimal totalDonatedAmount) {
        super(id, createdDate, modifiedDate);
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.scholarshipAmount = scholarshipAmount;
        this.currency = currency;
        this.period = period;
        this.duration = duration;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.totalDonatedAmount = totalDonatedAmount;
    }
}
