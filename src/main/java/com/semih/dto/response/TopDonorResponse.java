package com.semih.dto.response;

import java.math.BigDecimal;

public record TopDonorResponse(String firstName, String lastName, BigDecimal totalDonation) {
}
