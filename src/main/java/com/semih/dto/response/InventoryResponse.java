package com.semih.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse  extends BaseResponse{

    private Long id;

    private String productName; // Ürün adı (benzersiz)

    private int quantity; // Mevcut miktar

    private String unit;
}
