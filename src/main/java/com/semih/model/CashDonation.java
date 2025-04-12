package com.semih.model;

import com.semih.enums.CurrencyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cashDonation")
public class CashDonation extends BaseEntity {

    @ManyToOne
    private Donor donor;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    public CashDonation() {
    }

    public CashDonation(Long id, LocalDate createdDate, LocalDate modifiedDate, Donor donor, BigDecimal amount, CurrencyType currency) {
        super(id, createdDate, modifiedDate);
        this.donor = donor;
        this.amount = amount;
        this.currency = currency;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}
