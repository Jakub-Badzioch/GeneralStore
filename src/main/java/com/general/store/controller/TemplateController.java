package com.general.store.controller;

import com.general.store.mapper.TemplateMapper;
import com.general.store.model.dto.entityanalogue.impl.TemplateDTO;
import com.general.store.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "api/v1/templates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTemplate(@RequestBody TemplateDTO dto){
        templateService.save(templateMapper.toEntity(dto));
    }
}
