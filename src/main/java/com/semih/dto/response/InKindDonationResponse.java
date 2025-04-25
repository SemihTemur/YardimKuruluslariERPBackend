package com.semih.dto.response;

public record InKindDonationResponse(BaseResponse baseResponse,
                                     String donorFirstName,
                                     String donorLastName,
                                     CategoryUnitItemResponse category,
                                     Integer quantity
) {
}
