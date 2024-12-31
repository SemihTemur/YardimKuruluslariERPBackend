package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ınKindDonation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindDonation extends BaseEntity{

    @ManyToOne
    private Donor donor;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Integer quantity; // Adet (örneğin: 3)

}
