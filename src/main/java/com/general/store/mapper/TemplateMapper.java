package com.general.store.mapper;

import com.general.store.model.dao.impl.Template;
import com.general.store.model.dto.entityanalogue.impl.TemplateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    Template toEntity(TemplateDTO templateDTO);

    TemplateDTO toDto(Template template);

}
