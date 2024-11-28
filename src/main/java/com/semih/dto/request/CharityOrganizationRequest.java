package com.semih.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharityOrganizationRequest {

    private String charityOrganizationName;

    private String charityOrganizationPhoneNumber;

    private String charityOrganizationEmail;

    private String ownerName;

    private String ownerTckn;

    private BigDecimal budget;

    private AddressRequest address;
}
