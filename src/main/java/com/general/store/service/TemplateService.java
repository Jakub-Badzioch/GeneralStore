package com.general.store.service;

import com.general.store.model.dao.impl.Template;
import com.general.store.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public Template save(Template template){
        return templateRepository.save(template);
    }

    public Template getByName(String name){
        return templateRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException(name));
    }
}
