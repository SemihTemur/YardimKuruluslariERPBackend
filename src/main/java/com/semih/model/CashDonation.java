package com.semih.model;

import com.semih.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="cashDonation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CashDonation extends BaseEntity {

    @ManyToOne
    private Donor donor;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

}
