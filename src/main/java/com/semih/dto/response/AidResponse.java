package com.semih.dto.response;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AidResponse {

    private BigDecimal aidAmount;

    private CharityOrganizationResponse charityOrganization;

    private FamilyResponse family;

    private LocalDate startingDate;

    private LocalDate endingDate;

}
