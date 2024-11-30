package com.semih.dto.request;

import com.semih.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {

//    @NotBlank(message = "City name cannot be empty.")
//    @Size(min = 3, message = "City name must be at least 3 characters long.")
    private String city;

//    @NotBlank(message = "district name cannot be empty.")
//    @Size(min = 3, message = "district name must be at least 3 characters long.")
    private String district;

//    @NotBlank(message = "neighborhood name cannot be empty.")
//    @Size(min = 3, message = "neighborhood name must be at least 3 characters long.")
    private String neighborhood;

//    @NotBlank(message = "street name cannot be empty.")
//    @Size(min = 3, message = "street name must be at least 3 characters long.")
    private String street;

//    @NotNull(message = "userType cannot be null")
    private UserType userType;

}
