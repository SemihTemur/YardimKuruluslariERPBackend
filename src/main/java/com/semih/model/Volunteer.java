package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "volunteer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer extends BaseEntity{

    private String name;

    private String surname;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


}
