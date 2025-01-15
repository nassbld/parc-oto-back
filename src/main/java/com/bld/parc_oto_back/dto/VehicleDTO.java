package com.bld.parc_oto_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String licensePlate;
    private Long vehicleTypeId;
    private String status;
    private Long agencyId;
    private LocalDateTime endInsurance;
    private LocalDateTime endTechnicalControl;
}
