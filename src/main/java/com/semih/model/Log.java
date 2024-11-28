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
    private String action; // Örneğin: "Created", "Updated", "Deleted"

    @Column(nullable = false)
    private String entityName; // Örneğin: "Donation", "Event"

    @Column(nullable = false)
    private Long entityId; // İşlem yapılan entity'nin ID'si

    @Column(nullable = false)
    private String performedBy; // İşlemi yapan kullanıcının adı/ID'si

    @Column(nullable = false)
    private LocalDateTime timestamp; // İşlem zamanı
}

