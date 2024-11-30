package com.semih.dto.response;

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
public class DonationResponse {

    private BigDecimal amount;

    private String donationMessage;

    private CurrencyType currency;

    private DonationType donationType;

    private PaymentMethod paymentMethod;

    private CharityOrganizationResponse charityOrganization;

    private CategoryResponse category;

    private DonorResponse donor;

}
