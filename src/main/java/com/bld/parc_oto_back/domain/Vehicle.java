package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @Column(name = "end_insurance", nullable = false)
    private LocalDateTime endInsurance;

    @Column(name = "end_technical_control", nullable = false)
    private LocalDateTime endTechnicalControl;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    public Vehicle(Long id, String licensePlate, VehicleType type, VehicleStatus status, Agency agency, LocalDateTime endInsurance, LocalDateTime endTechnicalControl) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.status = status;
        this.agency = agency;
        this.endInsurance = endInsurance;
        this.endTechnicalControl = endTechnicalControl;
    }
}
