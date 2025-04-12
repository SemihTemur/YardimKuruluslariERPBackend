package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OtherIncomeResponse extends BaseResponse {

    private String description;

    private BigDecimal amount;

    private CurrencyType currency;

    public OtherIncomeResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String description, BigDecimal amount, CurrencyType currency) {
        super(id, createdDate, modifiedDate);
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }
}
