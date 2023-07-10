package com.general.store.model.dto.entityanalogue.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.general.store.model.dao.Basic;
import com.general.store.model.dto.entityanalogue.BasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountDTO extends BasicDTO {
    private String code;
    private Boolean isAvailable;
    private Double discountsPercentage;
    private LocalDateTime validDate;
}
