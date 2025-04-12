package com.semih.dto.response;

import com.semih.enums.GenderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class DonorResponse extends BaseResponse {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private GenderType genderType;

    private AddressResponse address;

    public DonorResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String firstName, String lastName, String phoneNumber, String email, GenderType genderType, AddressResponse address) {
        super(id, createdDate, modifiedDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderType = genderType;
        this.address = address;
    }

}
