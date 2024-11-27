package com.semih.model;

import com.semih.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address extends BaseEntity {

    private String city;

    private String district;

    private String neighborhood;

    private String street;

    @Enumerated(EnumType.STRING)
    private UserType userType;


}
