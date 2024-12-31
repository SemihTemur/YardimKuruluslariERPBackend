package com.semih.model;

import com.semih.enums.CurrencyType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "otherexpense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherExpense extends BaseEntity {

    private String description;

    private BigDecimal amount;

    private CurrencyType currency;

    private LocalDate expenseDate;

}
