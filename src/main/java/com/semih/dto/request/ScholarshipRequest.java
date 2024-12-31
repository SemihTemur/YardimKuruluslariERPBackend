package com.semih.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
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
public class ScholarshipRequest {

    //    @NotBlank(message = "studentName cannot be null or empty")
//    @Size(min = 3, message = "studentName must be at least 3 characters long")
    private String studentName;

    private String studentSurname;

    //    @NotNull(message = "amount cannot be null")
//    @DecimalMin(value = "10.00", message = "amount must be at least 10")
    private BigDecimal scholarshipAmount;

    private CurrencyType currency;

    private PeriodType period;

    private Integer duration;

}
