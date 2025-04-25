package com.semih.dto.response;

import com.semih.enums.GenderType;

public record DonorResponse(BaseResponse baseResponse,
                            String firstName,
                            String lastName,
                            String phoneNumber,
                            String email,
                            GenderType genderType,
                            AddressResponse address
) {

}
