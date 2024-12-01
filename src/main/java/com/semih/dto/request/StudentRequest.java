package com.semih.dto.request;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

//    @NotBlank(message = "name cannot be null or empty")
//    @Size(min = 3, message = "name must be at least 3 characters long")
    private String name;

//    @NotBlank(message = "surname cannot be null or empty")
//    @Size(min = 3, message = "surname must be at least 3 characters long")
    private String surname;

//    @NotNull(message = "familyMemberCount cannot be null")
//    @Min(value = 15,message = "familyMemberCount must be at least 15")
    private int age;

//    @NotBlank(message = "tckn  Number cannot be null or empty")
//    @Pattern(
//            regexp = "^[1-9][0-9]{10}$",
//            message = "TC Identity Number must be 11 digits and cannot start with 0"
//    )
    private String tckn;

//    @NotBlank(message = "Phone number cannot be null or empty")
//    @Pattern(regexp = "^\\+90\\d{10}$", message = "Phone number must be in the format +90XXXXXXXXXX")
    private String phoneNumber;


    //    @NotBlank(message = "student email cannot be null or empty")
//    @Email(message = "student email must be a valid email address")
    private String email;

//    @NotNull(message = "genderType cannot be null")
    private GenderType genderType;

//    @NotNull(message = "educationLevel cannot be null")
    private EducationLevel educationLevel;

//    @NotNull(message = "address cannot be null")
    private AddressRequest address;

}
