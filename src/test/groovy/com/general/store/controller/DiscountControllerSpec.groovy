package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.mapper.DiscountMapper
import com.general.store.model.dao.impl.Discount
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.entityanalogue.impl.DiscountDTO
import com.general.store.repository.DiscountRepository
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class DiscountControllerSpec extends Specification {

    @Autowired
    DiscountMapper discountMapper
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository
    @Autowired
    DiscountRepository discountRepository

    @WithMockUser
    def 'should add discount'() {
        given:
        userRepository.save(User.builder().email("user").build())
        def discountDTO = new DiscountDTO()
        expect:
        mockMvc.perform(post("/api/v2/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isCreated())
    }

    @WithMockUser
    def 'should get discount by id'() {
        given:
        def discount = discountRepository.save(new Discount())
        expect:
        mockMvc.perform(get("/api/v2/discounts/" + discount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0))
                .andExpect(jsonPath('$.code').doesNotExist())
                .andExpect(jsonPath('$.isAvailable').doesNotExist())
                .andExpect(jsonPath('$.discountsPercentage').doesNotExist())
                .andExpect(jsonPath('$.validDate').doesNotExist())
    }

    @WithMockUser
    def 'should update discount'() {
        // todo nie dziala, prawdopodobnie przez to ze uzywam @RequestPart zamiast @RequestBody.
        //  jak przetestowac ten przypadek?
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def discount = discountRepository.save(new Discount(isAvailable: true))
        def discountDTO = discountMapper.toDto(discount)
        discountDTO.setIsAvailable(false)
        expect:
        mockMvc.perform(put("/api/v2/discounts/" + discount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0))
                .andExpect(jsonPath('$.code').doesNotExist())
                .andExpect(jsonPath('$.isAvailable').value(false))
                .andExpect(jsonPath('$.discountsPercentage').doesNotExist())
                .andExpect(jsonPath('$.validDate').doesNotExist())
    }

    @WithMockUser
    def 'should delete discount'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def discount = discountRepository.save(new Discount())
        expect:
        mockMvc.perform(delete("/api/v2/discounts/" + discount.getId()))
                .andExpect(status().isNoContent())
    }
}
