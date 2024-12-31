package com.semih.model;

import com.semih.enums.PeriodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="ınKindAid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InKindAid extends BaseEntity {

    @ManyToOne
    private Family family;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Integer quantity; // Adet (örneğin: 3)

    @Enumerated(EnumType.STRING)
    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration; // kaç ... süreyle bu yardım yapılıcak

    @Column(nullable = false)
    private String totalDistributedQuantity;

    private LocalDate startingDate;

    private LocalDate endingDate;

}
