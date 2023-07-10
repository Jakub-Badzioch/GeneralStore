package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.mapper.AddressMapper
import com.general.store.model.dao.impl.Address
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.entityanalogue.impl.AddressDTO
import com.general.store.repository.AddressRepository
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class AddressControllerSpec extends Specification {

    @Autowired
    AddressMapper addressMapper
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository
    @Autowired
    AddressRepository addressRepository

    @WithMockUser
    def 'should add address'() {
        given:
        userRepository.save(User.builder().email("user").build())
        def addressDTO = new AddressDTO()
        expect:
        mockMvc.perform(post("/api/v3/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(status().isCreated())
    }

    @WithMockUser
    def 'should get address by id'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def address = addressRepository.save(new Address(user: user))
        expect:
        mockMvc.perform(get("/api/v3/addresses/" + address.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0))
                .andExpect(jsonPath('$.country').doesNotExist())
                .andExpect(jsonPath('$.city').doesNotExist())
                .andExpect(jsonPath('$.street').doesNotExist())
                .andExpect(jsonPath('$.postCode').doesNotExist())
                .andExpect(jsonPath('$.streetNumber').doesNotExist())
                .andExpect(jsonPath('$.apartmentNumber').doesNotExist())
    }

    @WithMockUser
    def 'should let you know that data couldn\'t be found when getting address by id'() {
        given:
        userRepository.save(User.builder().email("user").build())
        expect:
        mockMvc.perform(get("/api/v3/addresses/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$.message').value("Data couldn't be found"))
    }

    @WithMockUser
    def 'should update address'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def address = addressRepository.save(new Address(user: user, country: "Italy"))
        def addressDTO = addressMapper.toDto(address)
        addressDTO.setCountry("Poland")
        expect:
        mockMvc.perform(put("/api/v3/addresses/" + address.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0)) // address.getVersion() + 1 tak by bylo po trasakcji a nie jest po niej
                .andExpect(jsonPath('$.country').value("Poland"))
                .andExpect(jsonPath('$.city').doesNotExist())
                .andExpect(jsonPath('$.street').doesNotExist())
                .andExpect(jsonPath('$.postCode').doesNotExist())
                .andExpect(jsonPath('$.streetNumber').doesNotExist())
                .andExpect(jsonPath('$.apartmentNumber').doesNotExist())
    }

    @WithMockUser
    def 'should delete address'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def address = addressRepository.save(new Address(user: user))
        expect:
        mockMvc.perform(delete("/api/v3/addresses/" + address.getId()))
                .andExpect(status().isNoContent())
    }
}
