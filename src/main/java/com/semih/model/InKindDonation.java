package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ınKindDonation")
public class InKindDonation extends BaseEntity {

    @ManyToOne
    private Donor donor;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Integer quantity; // Adet (örneğin: 3)

    public InKindDonation() {
    }

    public InKindDonation(Donor donor, Category category, Integer quantity) {
        this.donor = donor;
        this.category = category;
        this.quantity = quantity;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
