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
@Table(name = "aid")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aid extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal aidAmount;

    @ManyToOne
    private CharityOrganization charityOrganization;

    @ManyToOne
    private Family family;

    @Column(nullable = false)
    private LocalDate startingDate;

    @Column(nullable = false)
    private LocalDate endingDate;

}
