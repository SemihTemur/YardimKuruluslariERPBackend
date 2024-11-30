package com.semih.dto.response;

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
public class ExpenseResponse {

    private BigDecimal amount; // Gider miktarı

    private LocalDate expenseDate; // Giderin kaydedildiği tarih

    private String description; // Gider açıklaması

    private CharityOrganizationResponse charityOrganization;
}
