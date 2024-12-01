package com.semih.dto.request;

import com.semih.enums.CurrencyType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharityOrganizationRequest {

//    @NotBlank(message = "charityOrganizationName cannot be null or empty")
//    @Size(min = 3, message = "charityOrganizationName must be at least 3 characters long")
    private String charityOrganizationName;

//    @NotBlank(message = "Phone number cannot be null or empty")
//    @Pattern(regexp = "^\\+90\\d{10}$", message = "Phone number must be in the format +90XXXXXXXXXX")
    private String charityOrganizationPhoneNumber;

//    @NotBlank(message = "charityOrganizationEmail cannot be null or empty")
//    @Email(message = "charityOrganizationEmail must be a valid email address")
    private String charityOrganizationEmail;
//
//    @NotBlank(message = "ownerName cannot be null or empty")
//    @Size(min = 3, message = "ownerName must be at least 3 characters long")
    private String ownerName;

//    @NotBlank(message = "ownerSurname cannot be null or empty")
//    @Size(min = 3, message = "ownerSurname must be at least 3 characters long")
    private String ownerSurname;

//    @NotBlank(message = "ownerTckn  Number cannot be null or empty")
//    @Pattern(
//            regexp = "^[1-9][0-9]{10}$",
//            message = "TC Identity Number must be 11 digits and cannot start with 0"
//    )
    private String ownerTckn;

//    @NotNull(message = "budget cannot be null")
    private BigDecimal budget;

    private CurrencyType currency = CurrencyType.TRY;

//    @NotNull(message = "address cannot be null")
    private AddressRequest address;
}
