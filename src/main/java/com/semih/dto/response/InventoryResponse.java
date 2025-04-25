package com.semih.dto.response;

public record InventoryResponse(BaseResponse baseResponse,
                                String productName,
                                int quantity,
                                String unit
) {

}
