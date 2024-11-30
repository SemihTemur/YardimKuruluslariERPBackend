package com.semih.dto.response;

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
public class IncomeResponse {

    private BigDecimal amount;

    private LocalDate incomeDate;

    private String description;

    private CharityOrganizationResponse charityOrganization;

}
