package com.semih.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name="Income")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Income extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDate incomeDate;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charity_organization_id")
    private CharityOrganization charityOrganization;
}
