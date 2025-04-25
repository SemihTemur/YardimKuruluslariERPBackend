package com.semih.dto.response;

public record CategoryResponse(BaseResponse baseResponse,
                               String itemName,
                               String unit
) {

}
