package com.general.store.mapper;

import com.general.store.model.dao.impl.Product;
import com.general.store.model.dto.entityanalogue.impl.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDTO productDTO);

    ProductDTO toDto(Product product);

    List<ProductDTO> toListDTO(List<Product> allProductsFromCart);

}

