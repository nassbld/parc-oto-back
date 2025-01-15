package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgencyMapper {

    @Mapping(target = "street", expression = "java(agency.getAddress().getStreet())")
    @Mapping(target = "postalCode", expression = "java(agency.getAddress().getPostalCode())")
    @Mapping(target = "city", expression = "java(agency.getAddress().getCity())")
    @Mapping(target = "country", expression = "java(agency.getAddress().getCountry())")
    @Mapping(target = "vehicleIds", ignore = true)
    AgencyDTO toDto(Agency agency);

    @Mapping(target = "address.street", source = "street")
    @Mapping(target = "address.postalCode", source = "postalCode")
    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.country", source = "country")
    @Mapping(target = "vehicles", ignore = true)
    Agency toEntity(AgencyDTO agencyDTO);
}
