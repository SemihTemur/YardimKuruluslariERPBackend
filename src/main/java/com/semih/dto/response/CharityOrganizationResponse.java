package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharityOrganizationResponse {

    private String organizationName;

    private String phoneNumber;

    private String email;

    private AddressResponse addressResponse;
}
