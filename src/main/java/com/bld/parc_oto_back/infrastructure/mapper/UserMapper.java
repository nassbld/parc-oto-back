package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.AgencyListDTO;
import com.bld.parc_oto_back.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "lastName", source = "last_name")
    @Mapping(target = "firstName", source = "first_name")
    UserDTO toDto(User user);

    @Mapping(target = "last_name", source = "lastName")
    @Mapping(target = "first_name", source = "firstName")
    User toEntity(UserDTO userDTO);

    @Mapping(target = "agencyIds", source = "favoriteAgencies")
    AgencyListDTO toAgencyListDTO(User user);

    default Long mapAgencyToId(Agency agency) {
        return agency.getId();
    }
}

