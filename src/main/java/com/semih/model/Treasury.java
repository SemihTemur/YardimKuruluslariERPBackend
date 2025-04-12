package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "treasury")
public class Treasury {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private BigDecimal balance; // Hazine bakiyesi (başlangıç bakiyesi + nakdi gelirler)

    private final String currency = "TRY";

    public Treasury() {
    }

    public Treasury(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }
}
