package com.general.store.model.dao.impl;

import com.general.store.model.OrdersStatus;
import com.general.store.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails extends Basic {
    @ManyToOne
    private User user;
    @ManyToOne
    private Address address;
    @Enumerated(EnumType.STRING)
    private OrdersStatus status;
    @ManyToOne
    private Discount discount;
    private BigDecimal totalPrice;
    private BigDecimal priceAfterDiscount;
}
