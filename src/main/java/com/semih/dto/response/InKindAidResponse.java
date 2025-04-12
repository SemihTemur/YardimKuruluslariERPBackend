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
public class InKindAidResponse extends BaseResponse {

    private String familyName;

    private CategoryResponse category;

    private Integer quantity;

    private PeriodType period;

    private Integer duration;

    private Integer totalDistributedQuantity;

    private LocalDate startingDate;

    private LocalDate endingDate;

    public InKindAidResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String familyName, CategoryResponse category, Integer quantity, PeriodType period, Integer duration, Integer totalDistributedQuantity, LocalDate startingDate, LocalDate endingDate) {
        super(id, createdDate, modifiedDate);
        this.familyName = familyName;
        this.category = category;
        this.quantity = quantity;
        this.period = period;
        this.duration = duration;
        this.totalDistributedQuantity = totalDistributedQuantity;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }
}
