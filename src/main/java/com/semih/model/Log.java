package com.semih.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String actionType; // Örneğin: "Oluşturuldu", "Güncellendi", "Silindi"

    @Column(nullable = false)
    private String targetEntity; // Örneğin: "Bağış", "Etkinlik"

    @Column(nullable = false)
    private String performedByUser; // İşlemi yapan kullanıcı (Adı veya ID)

    @Column(nullable = false)
    private LocalDateTime actionDate; // İşlem tarihi ve saati

    @Column
    private Double amount; // İşlemle ilgili para tutarı (Opsiyonel)

    @Column
    private String amountDescription; // Para ile ilgili açıklama (Opsiyonel, örn: "Kasadan 2000 TL düşüldü")

    public Log() {
    }

    public Log(Long id, String actionType, String targetEntity, String performedByUser, LocalDateTime actionDate, Double amount, String amountDescription) {
        this.id = id;
        this.actionType = actionType;
        this.targetEntity = targetEntity;
        this.performedByUser = performedByUser;
        this.actionDate = actionDate;
        this.amount = amount;
        this.amountDescription = amountDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String getPerformedByUser() {
        return performedByUser;
    }

    public void setPerformedByUser(String performedByUser) {
        this.performedByUser = performedByUser;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountDescription() {
        return amountDescription;
    }

    public void setAmountDescription(String amountDescription) {
        this.amountDescription = amountDescription;
    }
}

