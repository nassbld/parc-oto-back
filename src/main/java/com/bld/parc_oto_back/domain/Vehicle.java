package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String license_plate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING) // Sauvegarde en texte (e.g., "AVAILABLE")
    @Column(nullable = false)
    private VehicleStatus status;
}
