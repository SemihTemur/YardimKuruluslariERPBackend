package com.semih.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerRequest {
//
//    @NotBlank(message = "name cannot be null or empty")
//    @Size(min = 3, message = "name must be at least 3 characters long")
    private String name;

//    @NotBlank(message = "surname cannot be null or empty")
//    @Size(min = 3, message = "surname must be at least 3 characters long")
    private String surname;

//    @NotBlank(message = "Phone number cannot be null or empty")
//    @Pattern(regexp = "^\\+90\\d{10}$", message = "Phone number must be in the format +90XXXXXXXXXX")
    private String phone;

//    @NotNull(message = "address cannot be null")
    private AddressRequest address;

}
