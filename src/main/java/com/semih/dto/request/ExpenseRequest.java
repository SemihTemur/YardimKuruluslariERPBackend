package com.semih.dto.request;

import com.semih.model.CharityOrganization;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
//
//    @NotNull(message = "amount cannot be null")
//    @DecimalMin(value = "10.00", message = "amount must be at least 10")
    private BigDecimal amount;

//    @NotNull(message = "expenseDate cannot be null")
    private LocalDate expenseDate;

//    @NotBlank(message = "description cannot be null or empty")
//    @Size(min = 5, message = "description must be at least 5 characters long")
    private String description;

//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganizationName;

}
