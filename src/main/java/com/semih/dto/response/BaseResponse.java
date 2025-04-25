package com.semih.dto.response;

import java.time.LocalDate;

public record BaseResponse(Long id,
                           LocalDate createdDate,
                           LocalDate modifiedDate
) {

}
