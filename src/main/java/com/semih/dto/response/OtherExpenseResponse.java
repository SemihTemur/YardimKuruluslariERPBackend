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
@AllArgsConstructor
@NoArgsConstructor
public class OtherExpenseResponse extends BaseResponse {

    private String description;

    private BigDecimal amount;

    private CurrencyType currency;

    public OtherExpenseResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String description, BigDecimal amount, CurrencyType currency) {
        super(id, createdDate, modifiedDate);
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

   
}
