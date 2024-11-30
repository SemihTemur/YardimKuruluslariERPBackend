package com.semih.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AidRequest {

//    @NotNull(message = "aidAmount cannot be null")
//    @DecimalMin(value = "10.00", message = "aidAmount must be at least 10")
    private BigDecimal aidAmount;

//    @NotBlank(message = "organizationName cannot be null or empty")
//    @Size(min = 3, message = "organizationName must be at least 3 characters long")
    private String organizationName;

//    @NotBlank(message = "familyName cannot be null or empty")
//    @Size(min = 3, max = 12, message = "familyName must be between 3 and 12 characters long")
    private String familyName;

//    @NotNull(message = "startingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // JSON'da ISO formatı sağlar
    private LocalDate startingDate;
//
//    @NotNull(message = "endingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endingDate;
}
