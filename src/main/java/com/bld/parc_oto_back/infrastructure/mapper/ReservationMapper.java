package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.dto.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDto(Reservation entity);
    Reservation toEntity(ReservationDTO dto);
}