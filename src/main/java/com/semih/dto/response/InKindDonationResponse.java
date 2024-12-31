package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindDonationResponse {

    private String donorFirstName;

    private String donorLastName;

    private String donationMessage;

    private String itemName;

    private Integer quantity;

    private String unit;

}
