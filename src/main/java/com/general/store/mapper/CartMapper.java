package com.general.store.mapper;

import com.general.store.model.dao.impl.Cart;
import com.general.store.model.dto.entityanalogue.impl.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
}
