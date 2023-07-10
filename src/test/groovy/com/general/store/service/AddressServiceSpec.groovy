package com.general.store.service

import com.general.store.model.dao.enumeration.AddressType
import com.general.store.model.dao.impl.Address
import com.general.store.model.dao.impl.User
import com.general.store.repository.AddressRepository
import spock.lang.Specification

class AddressServiceSpec extends Specification {
    def addressRepository = Mock(AddressRepository)
    def userService = Mock(UserServiceSpec)
    def addressService = new AddressService(addressRepository, userService)

    def 'should get address by id'() {
        given:
        def address = Mock(Address)
        def id = Long.valueOf(1)

        when:
        addressService.getById(id)

        then:
        1 * addressRepository.getReferenceById(id) >> address
        0 * _
    }

    // jak podam do savea entity z podanym id to on to zignoruje i stworzy nową encje
    def 'should create address'() {
        given:
        def address = Mock(Address)
        def user = Mock(User)

        when:
        addressService.create(address)

        then:
        1 * userService.findCurrentlyLoggedIn() >> user
        1 * address.setUser(user)
        1 *  addressRepository.save(address) >> address
        0 * _
    }

    def 'should update address'() {
        given:
        def address = Mock(Address)
        def addressId = Long.valueOf(1)
        def country = "Poland"
        def city = "Poland"
        def street = "Poland"
        def postcode = "Poland"
        def streetNumber = "Poland"
        def apartmentNumber = "Poland"
        def addressType = AddressType.HOME

        when:
        addressService.update(address, addressId)

        then:
        1 * addressRepository.getReferenceById(1) >> address
        1 * address.getCountry() >> country
        1 * address.setCountry(country)
        1 * address.getCity() >> city
        1 * address.setCity(city)
        1 * address.getStreet() >> street
        1 * address.setStreet(street)
        1 * address.getPostCode() >> postcode
        1 * address.setPostCode(postcode)
        1 * address.getStreetNumber() >> streetNumber
        1 * address.setStreetNumber(streetNumber)
        1 * address.getApartmentNumber() >> apartmentNumber
        1 * address.setApartmentNumber(apartmentNumber)
        1 * address.getAddressType() >> addressType
        1 * address.setAddressType(addressType)
        0 * _
    }

    // todo czy powinienem testować EntityNotFoundException w jednostkowych i jeśli tak to jak?

    def 'should delete address'() {
        given:
        def id = Long.valueOf(1)
        def address = Mock(Address)

        when:
        addressService.delete(id)

        then:
        1 * addressRepository.getReferenceById(id) >> address
        1 * addressRepository.delete(address)
        0 * _
    }
}
