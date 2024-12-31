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

    private String donationMessage;

    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    private Integer quantity; // Adet (örneğin: 3)

    private String unit; // Birim (örneğin: "adet", "kg", "litre")

}
