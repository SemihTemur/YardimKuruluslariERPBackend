package com.semih.dto.request;

public record InKindInventoryRequest(String productName,
                                     int quantity,
                                     String unit
) {
    
}
