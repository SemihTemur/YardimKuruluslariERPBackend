package com.semih.dto.response;

import com.semih.enums.ProfitOrLoss;
import com.semih.model.CharityOrganization;
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
public class ReportResponse {

    private String title;

    private String description;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netBalance;

    private LocalDate startingDate;

    private LocalDate endingDate;

    private CharityOrganizationResponse charityOrganization;

    private ProfitOrLoss profitOrLoss;
}
