package com.bld.parc_oto_back.infrastructure.mapper;

import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
}
