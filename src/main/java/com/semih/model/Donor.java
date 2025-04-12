package com.semih.model;

import com.semih.enums.GenderType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "donor", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"}),
})
public class Donor extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "donor", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<CashDonation> cashDonation;

    @OneToMany(mappedBy = "donor", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<InKindDonation> inKindDonation;

    public Donor() {
    }

    public Donor(String firstName, String lastName, String phoneNumber, String email, GenderType genderType, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderType = genderType;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<CashDonation> getCashDonation() {
        return cashDonation;
    }

    public void setCashDonation(List<CashDonation> cashDonation) {
        this.cashDonation = cashDonation;
    }

    public List<InKindDonation> getInKindDonation() {
        return inKindDonation;
    }

    public void setInKindDonation(List<InKindDonation> inKindDonation) {
        this.inKindDonation = inKindDonation;
    }
}
