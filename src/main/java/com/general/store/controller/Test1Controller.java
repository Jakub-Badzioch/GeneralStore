package com.general.store.controller;

import com.general.store.service.Test1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class Test1Controller {

    // @transactional uzywa sie do updatow i jak dodaje/odejmuje dwie tabelki
    private final Test1Service test1Service;

    @GetMapping("/{id}")
    public void transactionals(@PathVariable Long id){
        test1Service.getById(id);
    }
}
