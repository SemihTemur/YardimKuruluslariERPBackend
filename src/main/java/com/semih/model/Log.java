package com.semih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}

