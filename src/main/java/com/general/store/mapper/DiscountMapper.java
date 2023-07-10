package com.general.store.mapper;

import com.general.store.model.dao.impl.Discount;
import com.general.store.model.dto.entityanalogue.impl.DiscountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(target = "id", ignore = true)
    Discount toEntity(DiscountDTO discountDTO);

    DiscountDTO toDto(Discount discount);

}
