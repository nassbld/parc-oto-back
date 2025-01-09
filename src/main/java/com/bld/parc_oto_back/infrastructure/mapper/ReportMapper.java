package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.dto.ReportDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDTO toDto(Report entity);
    Report toEntity(ReportDTO dto);
}
