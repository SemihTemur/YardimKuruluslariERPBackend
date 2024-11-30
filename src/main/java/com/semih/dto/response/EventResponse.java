package com.semih.dto.response;

import com.semih.enums.CurrencyType;
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
public class EventResponse {

    private String eventName;

    private String eventDescription;

    private BigDecimal eventIncome;

    private BigDecimal eventExpense;

    private LocalDate startingDate;

    private LocalDate endingDate;

    private CurrencyType currency;

    private CharityOrganizationResponse charityOrganization;

}
