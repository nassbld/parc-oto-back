package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.dto.ReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "reservationId", source = "reservation.id")
    ReportDTO toDto(Report report);

    @Mapping(target = "reservation", ignore = true)
    Report toEntity(ReportDTO reportDTO);
}

