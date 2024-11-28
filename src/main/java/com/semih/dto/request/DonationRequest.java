package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import com.semih.enums.DonationType;
import com.semih.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonationRequest {

    private String donorName;

    private String charityOrganizationName;

    private String donationMessage;

    private BigDecimal amount;

    private CurrencyType currency;

    private DonationType donationType;

    private PaymentMethod paymentMethod;

    private CategoryRequest category;

}
