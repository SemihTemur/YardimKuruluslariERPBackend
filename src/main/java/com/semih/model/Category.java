package com.semih.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity{

    @Column(unique=true, nullable=false)
    private String categoryName;

    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Donation> donations;

}
