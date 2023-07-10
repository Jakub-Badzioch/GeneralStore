package com.general.store.controller;

import com.general.store.model.dto.CredentialsDTO;
import com.general.store.model.dto.JwtTokenDTO;
import com.general.store.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
// todo dodaj json do kontrolerow, napisz formularz rejestracji z komunikacja na backend, dac dtosy tam gdzie ich nie ma
// jason_value to string a json to enum
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public JwtTokenDTO login(@RequestBody CredentialsDTO credentialsDTO) {
        return loginService.logIn(credentialsDTO.getEmail(), credentialsDTO.getPassword());
    }
}
