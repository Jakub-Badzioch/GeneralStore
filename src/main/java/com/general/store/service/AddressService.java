package com.general.store.service;

import com.general.store.model.dao.impl.Address;
import com.general.store.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public Address getById(Long addressId) {
        return addressRepository.getReferenceById(addressId);
    }

    public void create(Address address) {
        address.setUser(userService.findCurrentlyLoggedIn());
        addressRepository.save(address);
    }

    public Address update(Address address, Long addressId) {
        final Address addressDb = addressRepository.getReferenceById(addressId);
        addressDb.setCountry(address.getCountry());
        addressDb.setCity(address.getCity());
        addressDb.setStreet(address.getStreet());
        addressDb.setPostCode(address.getPostCode());
        addressDb.setStreetNumber(address.getStreetNumber());
        addressDb.setApartmentNumber(address.getApartmentNumber());
        addressDb.setAddressType(address.getAddressType());
        return addressDb;
    }

    public void delete(Long addressId) {
        addressRepository.delete(addressRepository.getReferenceById(addressId));
    }
}
