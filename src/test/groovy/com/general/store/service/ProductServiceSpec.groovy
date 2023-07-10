package com.general.store.service

import com.general.store.config.properties.FolderPropertiesConfig
import com.general.store.filehelper.FileHelper
import com.general.store.model.dao.impl.Product
import com.general.store.repository.ProductRepository
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Paths


class ProductServiceSpec extends Specification {
    def productRepository = Mock(ProductRepository)
    def folderPropertiesConfig = Mock(FolderPropertiesConfig)
    def fileHelper = Mock(FileHelper)
    def productService = new ProductService(productRepository, folderPropertiesConfig, fileHelper)

    def 'should filter products'() {
        given:
        def pageable = Mock(Pageable)
        def page = Mock(Page<Product>)

        when:
        productService.filter(priceStart, priceEnd, pageable)

        then:
        expectedFindBetween * productRepository.findDistinctByPriceIsBetween(priceStart, priceEnd, pageable) >> page
        expectedFindAll * productRepository.findAll(pageable)
        0 * _

        where:
        priceStart      | priceEnd               || expectedFindAll | expectedFindBetween
        BigDecimal.ZERO | BigDecimal.valueOf(20) || 0               | 1
        null            | null                   || 1               | 0
    }

    def 'should get product by id'() {
        given:
        def product = productRepository.save(Mock(Product))

        when:
        productService.getById(id)

        then:
        1 * productRepository.getReferenceById(id) >>> [product, null]

        where:
        id << [1, 2]
    }

    def 'should create product with image'() {
        given: "jakis tam opis "
        def product = Mock(Product)
        def image = Mock(MultipartFile)
        def path = Paths.get("target", "1")
        def completeFilePath = Paths.get("target", "1", "image.jpg")
        def imageInputStream = Mock(InputStream)

        when:
        productService.create(product, image)
        // tylko na mockach moge too sprawdac

        then:
        1 * productRepository.save(product) >> product
        1 * folderPropertiesConfig.getProduct() >> "target"
        1 * product.getId() >> 1
        1 * fileHelper.createDirectoryIfNotExists(path)
        1 * image.getOriginalFilename() >> "image.jpg"
        1 * image.getInputStream() >> imageInputStream
        1 * fileHelper.saveInputStreamAsFile(imageInputStream, completeFilePath)
        1 * product.setFilePath(completeFilePath.toString())
        0 * _

        where:
        b = true
    }
}
