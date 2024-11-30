package com.semih.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semih.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "aid")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aid extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal aidAmount;

    @ManyToOne(cascade = CascadeType.ALL)
    private CharityOrganization charityOrganization;

    @ManyToOne(cascade = CascadeType.ALL)
    private Family family;

    @Column(nullable = false)
    private LocalDate startingDate;

    @Column(nullable = false)
    private LocalDate endingDate;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

}
