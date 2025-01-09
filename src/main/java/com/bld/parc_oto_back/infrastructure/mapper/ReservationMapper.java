package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "reportIds", ignore = true)
    ReservationDTO toDto(Reservation entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "reports", ignore = true)
    Reservation toEntity(ReservationDTO dto);
}