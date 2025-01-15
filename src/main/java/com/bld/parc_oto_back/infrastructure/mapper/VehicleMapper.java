package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "agencyId", expression = "java(vehicle.getAgency().getId())")
    @Mapping(target = "vehicleTypeId", expression = "java(vehicle.getType().getId())")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(target = "agency.id", source = "agencyId")
    @Mapping(target = "type.id", source = "vehicleTypeId")
    Vehicle toEntity(VehicleDTO vehicleDTO);
}

