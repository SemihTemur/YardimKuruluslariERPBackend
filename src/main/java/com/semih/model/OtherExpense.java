package com.semih.model;

import com.semih.enums.CurrencyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "otherexpense")
public class OtherExpense extends BaseEntity {

    private String description;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    public OtherExpense() {
    }

    public OtherExpense(String description, BigDecimal amount, CurrencyType currency) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
