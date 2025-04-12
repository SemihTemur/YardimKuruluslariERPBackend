package com.semih.dto.response;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse extends BaseResponse {

    private String name;

    private String surname;

    private int age;

    private String tckn;

    private String phoneNumber;

    private String email;

    private GenderType genderType;

    private EducationLevel educationLevel;

    private AddressResponse address;

    public StudentResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String name, String surname, int age, String tckn, String phoneNumber, String email, GenderType genderType, EducationLevel educationLevel, AddressResponse address) {
        super(id, createdDate, modifiedDate);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.tckn = tckn;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderType = genderType;
        this.educationLevel = educationLevel;
        this.address = address;
    }
}
