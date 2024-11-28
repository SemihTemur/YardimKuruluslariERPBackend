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
@Table(name = "scholarship")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scholarship extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal scholarshipAmount;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private CharityOrganization charityOrganization;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Student student;

    private LocalDate startingDate;

    private LocalDate endingDate;


}
