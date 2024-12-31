package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InKindInventoryResponse {

    private String productName; // Ürün adı (benzersiz)

    private int quantity; // Mevcut miktar

    private String unit;

}
