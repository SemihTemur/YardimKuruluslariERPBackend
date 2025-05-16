package com.semih.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private String actionType; // Örneğin: "Oluşturuldu", "Güncellendi", "Silindi"

    @Column(nullable = false)
    private String targetEntity; // Örneğin: "Bağış", "Etkinlik"

    @Column(nullable = false)
    private String performedByUser; // İşlemi yapan kullanıcı (Adı veya ID)

    public Log() {
    }

    public Log(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String actionType, String targetEntity, String performedByUser) {
        this.id = id;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.actionType = actionType;
        this.targetEntity = targetEntity;
        this.performedByUser = performedByUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
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


}

