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
public class CharityOrganizationResponse {

    private String charityOrganizationName;

    private String charityOrganizationPhoneNumber;

    private String ownerName;

    private String ownerSurname;

    private BigDecimal budget;

    private AddressResponse address;

    private CurrencyType currency;

}
