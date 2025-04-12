package com.semih.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse extends BaseResponse {

    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    private String unit; // Birim (örneğin: "adet", "kg", "litre")

    public CategoryResponse(Long id, LocalDate createdDate, LocalDate modifiedDate, String itemName, String unit) {
        super(id, createdDate, modifiedDate);
        this.itemName = itemName;
        this.unit = unit;
    }

    public CategoryResponse(String itemName, String unit) {
        this.itemName = itemName;
        this.unit = unit;
    }
}

