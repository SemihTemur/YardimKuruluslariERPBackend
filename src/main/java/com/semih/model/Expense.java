package com.semih.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount; // Gider miktarı

    private LocalDate expenseDate; // Giderin kaydedildiği tarih

    private String description; // Gider açıklaması

    @ManyToOne
    @JoinColumn(name = "charity_organization_id")
    private CharityOrganization charityOrganization;
}
