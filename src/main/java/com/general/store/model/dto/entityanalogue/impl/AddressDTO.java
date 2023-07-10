package com.general.store.model.dto.entityanalogue.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.general.store.model.dao.enumeration.AddressType;
import com.general.store.model.dao.impl.User;
import com.general.store.model.dto.entityanalogue.BasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO extends BasicDTO {
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String streetNumber;
    private String apartmentNumber;
    private AddressType addressType;
}
