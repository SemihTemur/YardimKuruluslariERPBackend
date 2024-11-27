package com.semih.model;

import com.semih.enums.CurrencyType;
import com.semih.enums.DonationType;
import com.semih.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="donation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Donation extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Donor donor;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private CharityOrganization charityOrganization;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    private String donationMessage;

    @Column(nullable = false)
    private BigDecimal amount;


}
