package com.general.store.controller;

import com.general.store.mapper.UserMapper;
import com.general.store.model.dto.entityanalogue.impl.UserDTO;
import com.general.store.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    // todo czy te @PreAuthorize("isAuthenticated()") przed każdą metodą
    //  są ok czy można to jakoś zmienić i pozbyć sie boiler plate codu?
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @Operation(security = @SecurityRequirement(name = "security jwt"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }

    @Operation(security = @SecurityRequirement(name = "security jwt"))
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO updateUser(@PathVariable Long id, @RequestPart UserDTO userDTO) {
        return userMapper.toDto(userService.update(userMapper.toEntity(userDTO), id));
    }

    @Operation(security = @SecurityRequirement(name = "security jwt"))
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
