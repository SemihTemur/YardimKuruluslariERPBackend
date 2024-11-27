package com.semih.model;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    private String name;

    private String surname;

    private int age;

    private String tckn;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Scholarship> scholarship;

}
