package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "userId", expression = "java(reservation.getUser().getId())")
    @Mapping(target = "vehicleId", expression = "java(reservation.getVehicle().getId())")
    @Mapping(target = "reportIds", ignore = true)
    ReservationDTO toDto(Reservation reservation);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "vehicle.id", source = "vehicleId")
    @Mapping(target = "reports", ignore = true)
    Reservation toEntity(ReservationDTO dto);
}