package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgencyMapper {

    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "postalCode", source = "address.postalCode")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "vehicleIds", ignore = true)
    AgencyDTO toDto(Agency agency);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    Agency toEntity(AgencyDTO agencyDTO);
}
