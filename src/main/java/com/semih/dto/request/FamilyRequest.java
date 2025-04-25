package com.semih.dto.request;

public record FamilyRequest(String familyName,
                            int familyMemberCount,
                            String phoneNumber,
                            String email,
                            AddressRequest address
) {

}
