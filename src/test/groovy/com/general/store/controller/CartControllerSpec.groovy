package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.model.dao.impl.Address
import com.general.store.model.dao.impl.Product
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.AddProductDTO
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class CartControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    ProductRepository productRepository
    @Autowired
    UserRepository userRepository

    @WithMockUser
    def 'should add product to cart' () {
        // potrzebna byla stara wersja h2 i withMockUser ktory tworzy defaultowego usera trzeba go zapisac.
        // z tąa adnotacją nie ma logowania
        given:
        userRepository.save(User.builder().email("user").build())
        def product = productRepository.save(new Product(quantity: 25))
        def addProductDTO = new AddProductDTO(productId: product.getId(), neededQuantity: 10)

        expect:
        mockMvc.perform(post("/api/v3/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductDTO)))
                .andExpect(status().isCreated())
    }

    @WithMockUser
    def 'should get cart' () {
        given:
        userRepository.save(User.builder().email("user").build())
        def product = productRepository.save(new Product(quantity: 25))
        def addProductDTO = new AddProductDTO(productId: product.getId(), neededQuantity: 10)
        mockMvc.perform(post("/api/v3/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductDTO)))

        expect:
        // jak zwraca jeden to jest ok
        mockMvc.perform(get("/api/v3/carts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.length()').value(1))
                .andExpect(jsonPath('$[0].id').exists())
                .andExpect(jsonPath('$[0].createdDate').exists())
                .andExpect(jsonPath('$[0].lastModifiedDate').exists())
                .andExpect(jsonPath('$[0].createdBy').exists())
                .andExpect(jsonPath('$[0].lastModifiedBy').exists())
                .andExpect(jsonPath('$[0].version').value(0))
                .andExpect(jsonPath('$[0].name').doesNotExist())
                .andExpect(jsonPath('$[0].price').doesNotExist())
                .andExpect(jsonPath('$[0].filePath').doesNotExist())
                .andExpect(jsonPath('$[0].comments').doesNotExist())
                .andExpect(jsonPath('$[0].description').doesNotExist())
                .andExpect(jsonPath('$[0].quantity').value(10))
                .andExpect(jsonPath('$[0].scoreCount').doesNotExist())
                .andExpect(jsonPath('$[0].score').doesNotExist())
    }

    @WithMockUser
    def 'should delete product from cart'() {
        given:
        userRepository.save(User.builder().email("user").build())
        def product = productRepository.save(new Product(quantity: 25))
        def addProductDTO = new AddProductDTO(productId: product.getId(), neededQuantity: 10)
        mockMvc.perform(post("/api/v3/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductDTO)))

        expect:
        mockMvc.perform(delete("/api/v3/carts/" + product.getId()))
                .andExpect(status().isNoContent())
    }





    @WithMockUser
    def 'should let you know that data couldn\'t be found' () {
        given:
        def addProductDTO = new AddProductDTO(productId: 0, neededQuantity: 10)
        expect:
        mockMvc.perform(post("/api/v2/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$.message').value("Data couldn't be found"))
    }

    @WithMockUser
    def 'should get empty cart' () {
        given:
        userRepository.save(User.builder().email("user").build())
        expect:
        mockMvc.perform(get("/api/v2/carts"))
                .andExpect(status().isOk())
    }
}
