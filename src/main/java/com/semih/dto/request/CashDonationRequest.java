package com.semih.dto.request;

import com.semih.enums.CurrencyType;

import java.math.BigDecimal;

public record CashDonationRequest(String donorFirstName,
                                  String donorLastName,
                                  BigDecimal amount,
                                  CurrencyType currency
) {

}
