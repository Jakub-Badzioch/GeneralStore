package com.general.store.mapper;

import com.general.store.model.dao.impl.Address;
import com.general.store.model.dto.entityanalogue.impl.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDto(Address address);

}
