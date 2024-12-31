package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CashDonationResponse {

    private String donorFirstName;

    private String donorLastName;

    private BigDecimal amount;

    private CurrencyType currency;

    private String donationMessage;

}
