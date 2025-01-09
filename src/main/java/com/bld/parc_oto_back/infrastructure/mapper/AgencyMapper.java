package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgencyMapper {
    AgencyDTO toDto(Agency entity);
    Agency toEntity(AgencyDTO dto);
}