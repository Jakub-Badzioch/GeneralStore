package com.general.store.service

import com.general.store.model.dao.impl.Cart
import com.general.store.model.dao.impl.Product
import com.general.store.model.dao.impl.User
import com.general.store.repository.CartRepository
import spock.lang.Specification

class CartServiceSpec extends Specification {
    def cartRepository = Mock(CartRepository)
    def userService = Mock(UserServiceSpec)
    def productService = Mock(ProductService)
    def CartService = new CartService(cartRepository, productService, userService)

    def 'should add product'() {
        given:
        def product = Mock(Product)
        def productId = Long.valueOf(1)
        def neededQuantity = 10
        def actualQuantity = 12
        def userId = Long.valueOf(1)
        def user = Mock(User)
        def cart = Mock(Cart)
        def optionalCart = GroovyMock(Optional<Cart>)

        when:
        cartService.addProduct(productId, neededQuantity)

        then:
        1 * productService.getById(productId) >> product
        1 * product.getQuantity() >> actualQuantity
        1 * userService.findCurrentlyLoggedIn() >> user
        1 * user.getId() >> userId
        1 * cartRepository.findByProductIdAndUserId(1, 1) >> optionalCart
        1 * cartRepository.save(cart) >> cart
        0 * _
    }

    def 'should get all products from cart'() {
        given:
        def product = Mock(Product)
        def cart = Mock(Cart)
        def quantity = 1
        def userId = Long.valueOf(1)
        def user = Mock(User)
        def carts = new ArrayList<Cart>()
        carts.add(cart)

        when:
        cartService.getAllProductsFromCart()

        then:
        1 * userService.findCurrentlyLoggedIn() >> user
        1 * user.getId() >> userId
        1 * cartRepository.findByUserId(userId) >> carts
        1 * cart.getProduct() >> product
        1 * cart.getQuantity() >> quantity
        1 * product.setQuantity(quantity)
        0 * _
    }

    def 'should delete product from cart'() {
        given:
        def id = Long.valueOf(1)

        when:
        cartService.deleteProductFromCart(id)

        then:
        1 * cartRepository.deleteById(id)
        0 * _
    }

    def 'should delete whole cart'() {
        given:
        def id = Long.valueOf(1)
        def user = Mock(User)

        when:
        cartService.deleteWholeCart()

        then:
        1 * userService.findCurrentlyLoggedIn() >> user
        1 * user.getId() >> id
        1 * cartRepository.deleteByUserId(id)
        0 * _
    }

    def 'should get all carts'() {
        given:
        def id = Long.valueOf(1)
        def carts = Mock(List<Cart>)

        when:
        cartService.getAllCarts(id)

        then:
        1 * cartRepository.findByUserId(id) >> carts
        0 * _
    }
}
