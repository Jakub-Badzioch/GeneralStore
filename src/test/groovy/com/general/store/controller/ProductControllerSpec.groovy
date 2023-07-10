package com.general.store.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.general.store.model.dto.entityanalogue.impl.ProductDTO
import com.general.store.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class ProductControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    UserRepository userRepository


    @WithMockUser
    def 'should get filtered list of products'() {
    }


    @WithMockUser
    def 'should add product with photo to shop'() {
        given:
        def req = { request ->
            request.setMethod("POST")
            return request
        } as RequestPostProcessor
        def image = new MockMultipartFile("image", "smth.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0])
        def dto = new MockMultipartFile("productDTO", "", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(ProductDTO.builder()
                        .quantity(20)
                        .name("chleb pszenny")
                        .price(BigDecimal.valueOf(4.99))
                        .build()))
        expect:
        mockMvc.perform(multipart("/api/v3/products")
                .file(image)
                .file(dto)
                .with(req)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.createdDate').exists())
                .andExpect(jsonPath('$.lastModifiedDate').exists())
                .andExpect(jsonPath('$.createdBy').exists())
                .andExpect(jsonPath('$.lastModifiedBy').exists())
                .andExpect(jsonPath('$.version').value(0))
                .andExpect(jsonPath('$.quantity').value(20))
                .andExpect(jsonPath('$.name').value("chleb pszenny"))
                .andExpect(jsonPath('$.price').value(4.99))
    }
}
