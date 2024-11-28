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
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String eventName;

    private String eventDescription;

    private BigDecimal eventIncome;

    private BigDecimal eventExpense;

    private LocalDate startingDate;

    private LocalDate endingDate;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @ManyToOne
    private CharityOrganization charityOrganization;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<EventVolunteer> eventVolunteers;

}
