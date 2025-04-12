package com.semih.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindDonationRequest {

    private String donorFirstName;

    private String donorLastName;

    private CategoryRequest category;

    private Integer quantity; // Adet (örneğin: 3)
}
