package com.semih.model;

import com.semih.enums.PeriodType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ınKindAid")
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
    private Integer totalDistributedQuantity;

    private LocalDate startingDate;

    private LocalDate endingDate;

    public InKindAid() {
    }

    public InKindAid(Integer duration, PeriodType period, Integer quantity, Category category, Family family) {
        this.duration = duration;
        this.period = period;
        this.quantity = quantity;
        this.category = category;
        this.family = family;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Integer getTotalDistributedQuantity() {
        return totalDistributedQuantity;
    }

    public void setTotalDistributedQuantity(Integer totalDistributedQuantity) {
        this.totalDistributedQuantity = totalDistributedQuantity;
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
