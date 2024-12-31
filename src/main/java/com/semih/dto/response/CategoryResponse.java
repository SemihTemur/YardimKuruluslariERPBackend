package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {

    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    private String unit; // Birim (örneğin: "adet", "kg", "litre")

}
