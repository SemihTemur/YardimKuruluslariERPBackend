package com.semih.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semih.enums.ProfitOrLoss;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Report extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netBalance;

    private LocalDate startingDate;

    private LocalDate endingDate;

    @ManyToOne
    private CharityOrganization charityOrganization;

    @Enumerated(EnumType.STRING)
    private ProfitOrLoss profitOrLoss;


}
