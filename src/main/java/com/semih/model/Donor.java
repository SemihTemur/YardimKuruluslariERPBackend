package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="donor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Donor extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "donor",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Donation> donation;

}
