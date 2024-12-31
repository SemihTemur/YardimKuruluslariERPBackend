package com.semih.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "treasury")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Treasury{

    @Id
    private Long id=1L;

    @Column(nullable = false)
    private BigDecimal balance; // Hazine bakiyesi (başlangıç bakiyesi + nakdi gelirler)

    private final String currency="TRY" ;
}
