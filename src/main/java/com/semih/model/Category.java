package com.semih.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemName", "unit"})})
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    @Column(nullable = false)
    private String unit; // Birim (örneğin: "adet", "kg", "litre")

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<InKindAid> inKindAid;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<InKindDonation> inKindDonation;

    public Category() {
    }

    public Category(String itemName, String unit) {
        this.itemName = itemName;
        this.unit = unit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<InKindAid> getInKindAid() {
        return inKindAid;
    }

    public void setInKindAid(List<InKindAid> inKindAid) {
        this.inKindAid = inKindAid;
    }

    public List<InKindDonation> getInKindDonation() {
        return inKindDonation;
    }

    public void setInKindDonation(List<InKindDonation> inKindDonation) {
        this.inKindDonation = inKindDonation;
    }
}
