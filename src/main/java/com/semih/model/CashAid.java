package com.semih.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "cashAid")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CashAid extends BaseEntity{

    @ManyToOne
    private Family family;

    @Column(nullable = false)
    private BigDecimal aidAmount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    private PeriodType period; // aylık mı yıllık mı gunluk mu haftalık mı

    private Integer duration; // kaç ... süreyle bu yardım yapılıcak

    @Column(nullable = false)
    private BigDecimal totalDonatedAmount;

    // periodType ve duratıon'daki gelen değere gore arka planda ona gore bir tarihi ben ayarlıycam
    // eger eğer gunluk secıp 3 demiş ise e ornegın 5.12.2024'ten 8.12.2024'e kadar bır para yardımı yapıcam
    // bu 3 gunluk paranın tamamını ornegın gunluk 100 tl ise 3 gun 300 tl bunu gelır tablosuna eklıycem
    private LocalDate startingDate;

    private LocalDate endingDate;

}
