package com.semih.dto.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FamilyRequest {

//    @NotBlank(message = "familyName cannot be null or empty")
//    @Size(min = 3, max = 12, message = "familyName must be between 3 and 12 characters long")
    private String familyName;

//    @NotNull(message = "familyMemberCount cannot be null")
//    @Min(value = 2,message = "familyMemberCount must be at least 2")
//    @Max(value=12,message = "familyMemberCount must be max 12")
    private int familyMemberCount;
//
//    @NotBlank(message = "Phone number cannot be null or empty")
//    @Pattern(regexp = "^\\+90\\d{10}$", message = "Phone number must be in the format +90XXXXXXXXXX")
    private String phoneNumber;

//    @NotNull(message ="address cannot be null")
    private AddressRequest address;
}
