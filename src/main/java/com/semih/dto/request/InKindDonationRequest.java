package com.semih.dto.request;

public record InKindDonationRequest(String donorFirstName,
                                    String donorLastName,
                                    CategoryRequest category,
                                    Integer quantity
) {
    
}
