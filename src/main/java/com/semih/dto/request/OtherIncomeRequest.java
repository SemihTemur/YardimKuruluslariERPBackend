package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherIncomeRequest {

    private String description;

    private BigDecimal amount;

    private CurrencyType currency;

}
