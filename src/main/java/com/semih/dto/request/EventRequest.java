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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
//
//    @NotBlank(message = "eventName cannot be null or empty")
//    @Size(min = 3, message = "eventName must be at least 3 characters long")
    private String eventName;

//    @NotBlank(message = "eventDescription cannot be null or empty")
//    @Size(min = 3, message = "eventDescription must be at least 3 characters long")
    private String eventDescription;

//    @NotNull(message = "startingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startingDate;
//
//    @NotNull(message = "startingDate cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganizationName;

}
