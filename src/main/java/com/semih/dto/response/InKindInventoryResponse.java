package com.semih.dto.response;

public record InKindInventoryResponse(BaseResponse baseResponse,
                                      String productName,
                                      int quantity,
                                      String unit
) {
}
