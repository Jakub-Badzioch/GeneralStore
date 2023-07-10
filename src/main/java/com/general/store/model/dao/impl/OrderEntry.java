package com.general.store.model.dao.impl;

import com.general.store.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
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
public class OrderEntry extends Basic {
    @ManyToOne
    private Product product;
    private Long quantity;
    @ManyToOne
    private OrderDetails orderDetails;
    private BigDecimal unitPrice;
}
