package com.semih.dto.request;

import com.semih.enums.GenderType;

public record DonorRequest(String firstName,
                           String lastName,
                           String phoneNumber,
                           String email,
                           GenderType genderType,
                           AddressRequest address
) {

}
