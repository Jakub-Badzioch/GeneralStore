package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.model.dao.impl.Address
import com.general.store.model.dao.impl.Cart
import com.general.store.model.dao.impl.Discount
import com.general.store.model.dao.impl.Product
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.OrderCartDTO
import com.general.store.repository.AddressRepository
import com.general.store.repository.CartRepository
import com.general.store.repository.DiscountRepository
import com.general.store.repository.ProductRepository
import com.general.store.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class OrderControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository
    @Autowired
    AddressRepository addressRepository
    @Autowired
    DiscountRepository discountRepository
    @Autowired
    CartRepository cartRepository
    @Autowired
    ProductRepository productRepository

    @WithMockUser
    def 'should order cart'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def product = productRepository.save(new Product(price: 23, quantity: 10))
        cartRepository.save(new Cart(product: product, quantity: 3, user: user))
        def address = addressRepository.save(new Address(user: user))
        def discount = discountRepository.save(new Discount(code: "LATO", isAvailable: true, discountsPercentage: 0.3, validDate: LocalDateTime.MAX))
        def orderCartDTO = new OrderCartDTO(addressId: address.id, discountCode: discount.code)
        expect:
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCartDTO)))
                .andExpect(status().isCreated())
    }
}
