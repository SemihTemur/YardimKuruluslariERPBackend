package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="category")
@NoArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(unique=true)
    private String itemName; // Eşyanın adı (örneğin: "Çorap", "Ayakkabı", vb.)

    @Column(nullable = false)
    private String unit; // Birim (örneğin: "adet", "kg", "litre")

    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<InKindAid> inKindAid;

    @OneToMany(mappedBy="category",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<InKindDonation> inKindDonation;

    public Category(String itemName, String unit) {
        this.itemName = itemName;
        this.unit = unit;
    }

}
