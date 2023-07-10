package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.mapper.UserMapper
import com.general.store.model.dao.impl.Address
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.entityanalogue.impl.UserDTO
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
class UserControllerSpec extends Specification {

    @Autowired
    UserMapper userMapper
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository

    def 'should register user'() {
        given:
        def userDTO = new UserDTO(password: "Zupq4Romana")
        expect:
        mockMvc.perform(post("/api/v2/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
    }

    @WithMockUser
    def 'should get user by id'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def userDTO = userMapper.toDto(user)
        expect:
        mockMvc.perform(get("/api/v2/users/" + userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0))
                .andExpect(jsonPath('$.firstName').doesNotExist())
                .andExpect(jsonPath('$.lastName').doesNotExist())
                .andExpect(jsonPath('$.password').doesNotExist())
                .andExpect(jsonPath('$.email').value("user"))
    }

    @WithMockUser
    def 'should update address'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        def userDTO = userMapper.toDto(user)
        userDTO.setEmail("someEmail")
        expect:
        mockMvc.perform(put("/api/v2/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0)) // address.getVersion() + 1 tak by bylo po trasakcji a nie jest po niej
                .andExpect(jsonPath('$.firstName').doesNotExist())
                .andExpect(jsonPath('$.lastName').doesNotExist())
                .andExpect(jsonPath('$.password').doesNotExist())
                .andExpect(jsonPath('$.email').value("someEmail"))
    }

    @WithMockUser
    def 'should delete user'() {
        given:
        def user = userRepository.save(User.builder().email("user").build())
        expect:
        mockMvc.perform(delete("/api/v2/users/" + user.getId()))
                .andExpect(status().isNoContent())
    }
}
