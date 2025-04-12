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

    private CategoryRequest category;

    private Integer quantity; // Adet (örneğin: 3)

    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration; // kaç ... süreyle bu yardım yapılıcak
}
