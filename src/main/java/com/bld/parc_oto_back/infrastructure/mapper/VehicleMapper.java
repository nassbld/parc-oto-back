package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "licensePlate", source = "license_plate")
    @Mapping(target = "agencyId", source = "agency.id")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(target = "license_plate", source = "licensePlate")
    @Mapping(target = "agency", ignore = true)
    Vehicle toEntity(VehicleDTO vehicleDTO);
}

