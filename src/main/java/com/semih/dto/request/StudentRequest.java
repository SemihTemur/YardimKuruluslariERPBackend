package com.semih.dto.request;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private String name;

    private String surname;

    private int age;

    private String tckn;

    private String phoneNumber;

    private GenderType genderType;

    private EducationLevel educationLevel;

    private AddressRequest address;

}
