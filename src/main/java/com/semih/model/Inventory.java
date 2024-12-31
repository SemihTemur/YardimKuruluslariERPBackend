package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity{

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName; // Ürün adı (benzersiz)

    @Column(name = "quantity", nullable = false)
    private int quantity; // Mevcut miktar

    @Column(nullable = false)
    private String unit;

}
