package com.semih.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonorRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private AddressRequest address;
}
