package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity {

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName; // Ürün adı (benzersiz)

    @Column(name = "quantity", nullable = false)
    private int quantity; // Mevcut miktar

    @Column(nullable = false)
    private String unit;

    public Inventory() {
    }

    public Inventory(String productName, int quantity, String unit) {
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
