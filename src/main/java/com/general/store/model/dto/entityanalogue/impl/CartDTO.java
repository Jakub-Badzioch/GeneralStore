package com.general.store.model.dto.entityanalogue.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.general.store.model.dao.impl.Discount;
import com.general.store.model.dao.impl.Product;
import com.general.store.model.dao.impl.User;
import com.general.store.model.dto.entityanalogue.BasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO extends BasicDTO {
    private Product product;
    private Discount discount;
    private User user;
    private Long quantity;
}
