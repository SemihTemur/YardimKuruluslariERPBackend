package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "volunteer",uniqueConstraints = {@UniqueConstraint(columnNames = {"name","surname"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "volunteer", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<EventVolunteer> eventVolunteers;

}
