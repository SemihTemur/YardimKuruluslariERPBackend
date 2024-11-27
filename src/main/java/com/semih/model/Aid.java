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

    private BigDecimal aidAmount;

    @ManyToOne
    private CharityOrganization charityOrganization;

    @ManyToOne
    private Family family;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // JSON'da ISO formatı sağlar
    private LocalDate startingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

}
