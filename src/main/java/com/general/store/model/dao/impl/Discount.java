package com.general.store.model.dao.impl;

import com.general.store.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends Basic {
    private String code;
    private Boolean isAvailable;
    private Double discountsPercentage;
    private LocalDateTime validDate;
}
