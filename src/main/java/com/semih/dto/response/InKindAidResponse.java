package com.semih.dto.response;

import com.semih.enums.PeriodType;

import java.time.LocalDate;

public record InKindAidResponse(BaseResponse baseResponse,
                                String familyName,
                                CategoryResponse category,
                                Integer quantity,
                                PeriodType period,
                                Integer duration,
                                Integer totalDistributedQuantity,
                                LocalDate startingDate,
                                LocalDate endingDate
) {
}
