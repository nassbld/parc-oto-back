package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.VehicleType;
import com.bld.parc_oto_back.dto.VehicleTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {

    VehicleTypeMapper INSTANCE = Mappers.getMapper(VehicleTypeMapper.class);

    VehicleTypeDTO toDTO(VehicleType vehicleType);

    VehicleType toEntity(VehicleTypeDTO vehicleTypeDTO);
}
