package com.semih.dto.response;

import com.semih.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindAidResponse {

    private String familyName;

    private String itemName;

    private Integer quantity;

    private String unit;

    private PeriodType period;

    private Integer duration;

    private String totalDistributedQuantity ;

    private LocalDate startingDate;

    private LocalDate endingDate;

}
