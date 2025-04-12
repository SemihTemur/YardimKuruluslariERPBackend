package com.semih.model;

import com.semih.enums.EducationLevel;
import com.semih.enums.GenderType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})
})
public class Student extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private int age;

    @Column(unique = true, nullable = false)
    private String tckn;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Scholarship> scholarship;

    public Student() {
    }

    public Student(String name, String surname, int age, String tckn, String phoneNumber, String email, GenderType genderType, EducationLevel educationLevel, Address address) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.tckn = tckn;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderType = genderType;
        this.educationLevel = educationLevel;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
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

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Scholarship> getScholarship() {
        return scholarship;
    }

    public void setScholarship(List<Scholarship> scholarship) {
        this.scholarship = scholarship;
    }
}
