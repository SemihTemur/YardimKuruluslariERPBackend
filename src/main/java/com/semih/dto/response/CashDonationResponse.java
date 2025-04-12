package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class CashDonationResponse extends BaseResponse {

    private String donorFirstName;

    private String donorLastName;

    private BigDecimal amount;

    private CurrencyType currency;

    public CashDonationResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String donorFirstName, String donorLastName, BigDecimal amount, CurrencyType currency) {
        super(id, createdDate, modifiedDate);
        this.donorFirstName = donorFirstName;
        this.donorLastName = donorLastName;
        this.amount = amount;
        this.currency = currency;
    }
}
