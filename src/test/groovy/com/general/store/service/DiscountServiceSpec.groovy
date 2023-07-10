package com.general.store.service

import com.general.store.model.dao.impl.Cart
import com.general.store.model.dao.impl.Discount
import com.general.store.repository.DiscountRepository
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class DiscountServiceSpec extends Specification {

    def discountRepository = Mock(DiscountRepository)
    def discountService = new DiscountService(discountRepository)

    def 'should get discount by id'() {
        given:
        def discount = Mock(Discount)
        def id = Long.valueOf(1)

        when:
        discountService.getById(id)

        then:
        1 * discountRepository.getReferenceById(id) >> discount
        0 * _
    }

    def 'should get discount by discount code'() {
        given:
        def discount = Mock(Discount)
        def code = "AUTUMN"
        def optionalDiscount = GroovyMock(Optional<Discount>)

        when:
        discountService.getByDiscountCode(code)

        then:
        1 * discountRepository.findByCodeAndIsAvailableIsTrue(code) >> optionalDiscount
        0 * _
    }

    def 'should create discount'() {
        given:
        def discount = Mock(Discount)

        when:
        discountService.create(discount)

        then:
        discountRepository.save(discount) >> discount
    }


}
