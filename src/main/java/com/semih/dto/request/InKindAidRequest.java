package com.semih.dto.request;

import com.semih.enums.PeriodType;

public record InKindAidRequest(String familyName,
                               CategoryRequest category,
                               Integer quantity,
                               PeriodType period,
                               Integer duration
) {

}
