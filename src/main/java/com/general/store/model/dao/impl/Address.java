package com.general.store.model.dao.impl;

import com.general.store.model.dao.Basic;
import com.general.store.model.dao.enumeration.AddressType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends Basic {
    @ManyToOne
    private User user;
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String streetNumber;
    private String apartmentNumber;
    private AddressType addressType;
}
