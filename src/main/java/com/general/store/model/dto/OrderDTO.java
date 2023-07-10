package com.general.store.model.dto;

import com.general.store.model.OrdersStatus;
import com.general.store.model.dao.impl.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private User user;
    private Address address;
    private OrdersStatus status;
    private Product product;
    private Discount discount;
    private Long quantity;
    private OrderDetails orderDetails;
    private BigDecimal totalPrice;
    private BigDecimal priceAfterDiscount;
}
