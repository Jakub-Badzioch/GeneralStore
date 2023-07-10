package com.general.store.model.dto.entityanalogue.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.general.store.model.dto.entityanalogue.BasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BasicDTO {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Incorrect password. Can't be null and length with/without whitespaces must be longer than 0.")
    @Pattern(regexp = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])).{10,}",
            message = "Incorrect password. Must be at least: 1 int, 1 lowercase, 1 uppercase, 10 chars.")
    private String password;
    private String email;
}
