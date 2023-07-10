package com.general.store.service

import com.general.store.model.OrdersStatus
import com.general.store.model.dao.impl.*
import com.general.store.repository.OrderDetailsRepository
import com.general.store.repository.OrderEntryRepository
import spock.lang.Specification

import java.util.stream.Collectors

class OrderServiceSpec extends Specification {
  /*  def cartService = Mock(CartService)
    def userService = Mock(UserService)
    def addressService = Mock(AddressService)
    def discountService = Mock(DiscountService)
    def orderDetailsRepository = Mock(OrderDetailsRepository)
    def orderEntryRepository = Mock(OrderEntryRepository)
    def productService = Mock(ProductService)
    def orderService = new OrderService(cartService, userService, addressService, discountService,
            orderDetailsRepository, orderEntryRepository, productService)

    def 'should orderCart'() {
        given:
        def addressId = 1
        def address = Mock(Address)
        def discountCode = "LATO"
        def discount = Mock(Discount)
        def user = Mock(User)
        def carts = Mock(List<Cart>)
        def orderDetails = Mock(OrderDetails)
        def orderEntries = Mock(List<OrderEntry>)
        def totalPrice = 39

        when:
        orderService.orderCart(addressId, discountCode)

        then:
        1 * userService.findCurrentlyLoggedIn() >> user
        1 * addressService.getById(addressId) >> address
        1 * discountService.getByDiscountCode(discountCode) >> discount
        1 * cartService.getAllCarts(user.getId()) >> carts
        1 * OrderDetails.builder()
                .address(address)
                .discount(discount)
                .user(user)
                .status(OrdersStatus.CREATED)
                .build() >> orderDetails
        1 * carts.stream().map(cart -> OrderEntry.builder()
   *//*             .product(cart.getProduct())
                .quantity(cart.getQuantity())
                .unitPrice(cart.getProduct().getPrice())*//*
                .orderDetails(orderDetails)
                .build())
                .collect(Collectors.toList()) >> orderEntries
        1 * orderEntries.stream()
            //    .map(orderEntry -> orderEntry.getUnitPrice().multiply(BigDecimal.valueOf(orderEntry.getQuantity())))
             //   .reduce(BigDecimal.ZERO, BigDecimal::add) >> totalPrice
        1 * orderDetailsRepository.save(orderDetails) >> orderDetails
        0 * _
    }*/
}
