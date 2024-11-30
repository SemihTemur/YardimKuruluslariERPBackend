package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import com.semih.enums.DonationType;
import com.semih.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

//    @NotBlank(message = "donorName cannot be null or empty")
//    @Size(min = 3, message = "donorName must be at least 3 characters long")
    private String donorName;

//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganizationName;

//    @NotBlank(message = "donationMessage cannot be null or empty")
//    @Size(min = 3, message = "donationMessage must be at least 3 characters long")
    private String donationMessage;
//
//    @NotNull(message = "amount cannot be null")
//    @DecimalMin(value = "10.00", message = "amount must be at least 10")
    private BigDecimal amount;

//    @NotNull(message = "currency cannot be null")
    private CurrencyType currency;

//    @NotNull(message = "donationType cannot be null")
    private DonationType donationType;

//    @NotNull(message = "paymentMethod cannot be null")
    private PaymentMethod paymentMethod;

//    @NotNull(message = "category cannot be null")
    private CategoryRequest category;

}
