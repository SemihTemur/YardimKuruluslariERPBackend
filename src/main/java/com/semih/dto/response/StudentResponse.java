package com.semih.dto.response;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;

public record StudentResponse(BaseResponse baseResponse,
                              String name,
                              String surname,
                              int age,
                              String tckn,
                              String phoneNumber,
                              String email,
                              GenderType genderType,
                              EducationLevel educationLevel,
                              AddressResponse address
) {
}
