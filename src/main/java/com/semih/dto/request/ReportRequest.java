package com.semih.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportRequest {

//    @NotBlank(message = "title cannot be null or empty")
//    @Size(min = 4, message = "title must be at least 4 characters long")
    private String title;

//    @NotBlank(message = "description cannot be null or empty")
//    @Size(min = 5, message = "description must be at least 5 characters long")
    private String description;
//
//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganization;

//    @NotNull(message = "startingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startingDate;

//    @NotNull(message = "endingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

}
