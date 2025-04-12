package com.semih.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "family")
public class Family extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String familyName;

    private int familyMemberCount;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "family", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<CashAid> cashAid;

    @OneToMany(mappedBy = "family", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<InKindAid> inKindAid;

    public Family() {
    }

    public Family(String familyName, int familyMemberCount, String phoneNumber, String email, Address address) {
        this.familyName = familyName;
        this.familyMemberCount = familyMemberCount;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getFamilyMemberCount() {
        return familyMemberCount;
    }

    public void setFamilyMemberCount(int familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<CashAid> getCashAid() {
        return cashAid;
    }

    public void setCashAid(List<CashAid> cashAid) {
        this.cashAid = cashAid;
    }

    public List<InKindAid> getInKindAid() {
        return inKindAid;
    }

    public void setInKindAid(List<InKindAid> inKindAid) {
        this.inKindAid = inKindAid;
    }
}
