package com.semih.dto.response;

public record FamilyResponse(
        BaseResponse baseResponse,
        String familyName,
        int familyMemberCount,
        String phoneNumber,
        String email,
        AddressResponse address
) {
}
