package com.semih.model;

import com.semih.enums.GenderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="donor",uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName","lastName"}),
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "donor",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<CashDonation> cashDonation;

    @OneToMany(mappedBy = "donor",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<InKindDonation> inKindDonation;

}
