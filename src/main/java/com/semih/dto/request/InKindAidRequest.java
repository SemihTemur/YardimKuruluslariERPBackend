package com.semih.dto.request;

import com.semih.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindAidRequest {

    private String familyName;

    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    private Integer quantity; // Adet (örneğin: 3)

    private String unit; // Birim (örneğin: "adet", "kg", "litre")

    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration; // kaç ... süreyle bu yardım yapılıcak
}
