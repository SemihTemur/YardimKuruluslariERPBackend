package com.semih.model;

import com.semih.enums.CurrencyType;
import com.semih.enums.DonationType;
import com.semih.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="donation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Donation extends BaseEntity {

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

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    private Donor donor;

    @ManyToOne(cascade = CascadeType.ALL)
    private CharityOrganization charityOrganization;

}
