package com.bld.parc_oto_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithInfosDTO {
    private Long id;
    private String userFirstName;
    private String userLastName;
    private String userMatricule;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleLicensePlate;
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;
}
