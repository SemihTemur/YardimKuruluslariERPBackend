package com.semih.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class InKindDonationResponse extends BaseResponse {

    private String donorFirstName;

    private String donorLastName;

    private CategoryUnitItemResponse category;

    private Integer quantity;

    public InKindDonationResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String donorFirstName, String donorLastName, CategoryUnitItemResponse category, Integer quantity) {
        super(id, createdDate, modifiedDate);
        this.donorFirstName = donorFirstName;
        this.donorLastName = donorLastName;
        this.category = category;
        this.quantity = quantity;
    }


}
