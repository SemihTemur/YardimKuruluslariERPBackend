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
@Table(name="Income")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Income extends BaseEntity {

    private BigDecimal amount; // Gelir miktarı

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate income_date; // Gelirin kaydedildiği tarih

    private String description; // Gelir açıklaması

    @ManyToOne
    @JoinColumn(name = "charity_organization_id")
    private CharityOrganization charityOrganization; // Hangi derneğe ait olduğu
}
