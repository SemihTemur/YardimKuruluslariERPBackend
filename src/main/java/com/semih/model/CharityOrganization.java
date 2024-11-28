package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "charityOrganization")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharityOrganization extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String charityOrganizationName;

    @Column(unique = true, nullable = false)
    private String charityOrganizationPhoneNumber;

    @Column(unique = true, nullable = false)
    private String charityOrganizationEmail;

    @Column(unique = true, nullable = false)
    private String ownerName;

    @Column(unique = true, nullable = false)
    private String ownerTckn;

    @Column(nullable = false)
    private BigDecimal budget;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Income> income;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Expense> expense;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Aid> aid;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Donation> donation;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Event> event;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Report> report;

    @OneToMany(mappedBy = "charityOrganization", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Scholarship> scholarship;
}
