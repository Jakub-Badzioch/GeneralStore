package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.mapper.AddressMapper
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.entityanalogue.impl.AddressDTO
import com.general.store.model.dto.entityanalogue.impl.TemplateDTO
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class TemplateControllerSpec extends Specification{
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository

    @WithMockUser
    def 'should add template'() {
        given:
        userRepository.save(User.builder().email("user").build())
        def templateDTO = new TemplateDTO()
        expect:
        mockMvc.perform(post("/api/v1/templates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateDTO)))
                .andExpect(status().isCreated())
    }
}
