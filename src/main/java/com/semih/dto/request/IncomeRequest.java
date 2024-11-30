package com.semih.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class IncomeRequest {

//    @NotNull(message = "amount cannot be null")
//    @DecimalMin(value = "10.00", message = "amount must be at least 10")
    private BigDecimal amount;

//    @NotNull(message = "incomeDate cannot be null")
    private LocalDate incomeDate;

//    @NotBlank(message = "description cannot be null or empty")
//    @Size(min = 5, message = "description must be at least 5 characters long")
    private String description;

//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganizationName;

}
