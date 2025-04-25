package com.semih.dto.request;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;

public record StudentRequest(String name,
                             String surname,
                             int age,
                             String tckn,
                             String phoneNumber,
                             String email,
                             GenderType genderType,
                             EducationLevel educationLevel,
                             AddressRequest address) {
    
}
