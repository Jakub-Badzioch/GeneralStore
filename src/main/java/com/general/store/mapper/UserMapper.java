package com.general.store.mapper;

import com.general.store.model.dao.impl.User;
import com.general.store.model.dto.entityanalogue.impl.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    UserDTO toDto(User user);

}
