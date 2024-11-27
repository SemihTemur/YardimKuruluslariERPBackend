package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "family")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Family extends BaseEntity {

    private String familyName;

    private int familyMemberCount;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "family",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Aid> aid;

}
