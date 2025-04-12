package com.semih.dto.response;

import java.time.LocalDate;

public record FamilyResponse(
        Long id,
        LocalDate createdDate,
        LocalDate modifiedDate,
        String familyName,
        int familyMemberCount,
        String phoneNumber,
        String email,
        AddressResponse address
) {
}
