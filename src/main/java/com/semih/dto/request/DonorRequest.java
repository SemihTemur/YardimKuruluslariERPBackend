package com.semih.dto.request;

import com.semih.enums.GenderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonorRequest {

//    @NotBlank(message = "firstName cannot be null or empty")
//    @Size(min = 3, message = "firstName must be at least 3 characters long")
    private String firstName;

//    @NotBlank(message = "lastName cannot be null or empty")
//    @Size(min = 3, message = "lastName must be at least 3 characters long")
    private String lastName;
//
//    @NotBlank(message = "Phone number cannot be null or empty")
//    @Pattern(regexp = "^\\+90\\d{10}$", message = "Phone number must be in the format +90XXXXXXXXXX")
    private String phoneNumber;
//
//    @NotBlank(message = "email cannot be null or empty")
//    @Email(message = "email must be a valid email address")
    private String email;

    private GenderType genderType;

//    @NotNull(message = "address cannot be null")
    private AddressRequest address;
}
