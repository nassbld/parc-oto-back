package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Une réservation peut avoir plusieurs signalements
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false, length = 1000)
    private String description; // Description du problème signalé

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type; // Type de signalement (e.g., véhicule endommagé, retard)

    @Column(nullable = false)
    private LocalDateTime reportDateTime; // Date et heure du signalement
}
