package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipResponse {

    private BigDecimal scholarshipAmount;

    private CharityOrganizationResponse charityOrganization;

    private StudentResponse student;

    private LocalDate startingDate;

    private LocalDate endingDate;

}
