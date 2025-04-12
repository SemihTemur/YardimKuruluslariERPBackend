package com.semih.model;

import com.semih.enums.UserType;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String street;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Address() {
    }

    public Address(String city, String district, String neighborhood, String street, UserType userType) {
        this.city = city;
        this.district = district;
        this.neighborhood = neighborhood;
        this.street = street;
        this.userType = userType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
