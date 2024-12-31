package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FamilyResponse {

    private String familyName;

    private int familyMemberCount;

    private String phoneNumber;

    private String email;

    private AddressResponse address;

}
