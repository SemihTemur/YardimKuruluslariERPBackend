package com.semih.dto.response;

import com.semih.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonorResponse {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private GenderType genderType;

    private AddressResponse address;

}
