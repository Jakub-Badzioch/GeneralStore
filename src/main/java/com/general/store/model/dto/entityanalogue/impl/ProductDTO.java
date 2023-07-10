package com.general.store.model.dto.entityanalogue.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.general.store.model.dao.impl.Comment;
import com.general.store.model.dto.entityanalogue.BasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO extends BasicDTO {
    private String name;
    private BigDecimal price;
    private String filePath;
    // todo zmień na dto
    private List<Comment> comments;
    private String description;
    private Long quantity;
    private Integer scoreCount;
    private Double score;
}