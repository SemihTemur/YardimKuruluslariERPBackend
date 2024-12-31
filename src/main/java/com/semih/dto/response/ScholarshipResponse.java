package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipResponse {

    private String studentName;

    private String studentSurname;

    private BigDecimal scholarshipAmount;

    private CurrencyType currency;

    private PeriodType period;

    private Integer duration;

    private LocalDate startingDate;

    private LocalDate endingDate;

    private BigDecimal totalDonatedAmount;

}
