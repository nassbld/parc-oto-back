package com.bld.parc_oto_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleIdentityDTO {
    private Long id;

    private Long vehicleId;
    private String licensePlate;
    private Long agencyId;

    private Long vehicleTypeId;
    private String brand;
    private String model;
    private String imageUrl;
}
