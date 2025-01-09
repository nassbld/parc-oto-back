package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.dto.VehicleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleDTO toDto(Vehicle entity);
    Vehicle toEntity(VehicleDTO dto);
}
