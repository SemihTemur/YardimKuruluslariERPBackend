package com.semih.dto.response;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private String name;

    private String surname;

    private int age;

    private String tckn;

    private String phoneNumber;

    private String email;

    private GenderType genderType;

    private EducationLevel educationLevel;

}
