package com.semih.dto.response;

import java.math.BigDecimal;

public record MonthlyDonationStatsResponse(String donation_month, String donor_name, String last_name,
                                           BigDecimal total_donation, BigDecimal average_donation) {
}
