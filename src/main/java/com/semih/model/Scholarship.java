package com.semih.model;

import com.semih.enums.CurrencyType;
import com.semih.enums.PeriodType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "scholarship")

public class Scholarship extends BaseEntity {

    @ManyToOne
    private Student student;

    @Column(nullable = false)
    private BigDecimal scholarshipAmount;

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

    public Scholarship() {
    }

    public Scholarship(Long id, LocalDate createdDate, LocalDate modifiedDate, Student student, BigDecimal scholarshipAmount, CurrencyType currency, PeriodType period, Integer duration, BigDecimal totalDonatedAmount, LocalDate startingDate, LocalDate endingDate) {
        super(id, createdDate, modifiedDate);
        this.student = student;
        this.scholarshipAmount = scholarshipAmount;
        this.currency = currency;
        this.period = period;
        this.duration = duration;
        this.totalDonatedAmount = totalDonatedAmount;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getTotalDonatedAmount() {
        return totalDonatedAmount;
    }

    public void setTotalDonatedAmount(BigDecimal totalDonatedAmount) {
        this.totalDonatedAmount = totalDonatedAmount;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }
}
