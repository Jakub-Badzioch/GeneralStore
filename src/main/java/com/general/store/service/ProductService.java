package com.general.store.service;

import com.general.store.config.properties.FolderPropertiesConfig;
import com.general.store.filehelper.FileHelper;
import com.general.store.model.dao.impl.Product;
import com.general.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final FolderPropertiesConfig folderPropertiesConfig;
    private final FileHelper fileHelper;

    // price start i end jest null
    // price start jest nie null
    // end nie jest null
    // start i end sa nie null
    public Page<Product> filter(BigDecimal priceStart, BigDecimal priceEnd, Pageable pageable) {
        if (priceStart == null || priceEnd == null) return productRepository.findAll(pageable);
        return productRepository.findDistinctByPriceIsBetween(priceStart, priceEnd, pageable);
    }

    // adnotacja dziala tak ze sprawdza czy w mapie jest ten produkt po idku. jesli jest to zwraca go uzytkownikowi i nie wykonuje metody ze serwisu
    // doda jesli jeszcze nie istnieje
    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Transactional
    // doda
    @CachePut(cacheNames = "product", key = "#result.id")
    public Product create(Product product, MultipartFile image) {
        productRepository.save(product);
        try {
            final Path path = Paths.get(folderPropertiesConfig.getProduct(), product.getId().toString());
            fileHelper.createDirectoryIfNotExists(path);
            final Path completeFilePath = Paths.get(path.toString(), image.getOriginalFilename());
            fileHelper.saveInputStreamAsFile(image.getInputStream(), completeFilePath);
            product.setFilePath(completeFilePath.toString());
        } catch (Exception e) {
            log.warn("Couldn't save file", e);
        }
        return product;
    }

    public void replace(Product product) {
        productRepository.save(product);
    }

    @Transactional
    // doda albo podmieni jesli istnieje juz taki klucz
    @CachePut(cacheNames = "product", key = "#result.id")
    public Product update(Product product, MultipartFile image, Long productId) {
        final Product productDb = productRepository.getReferenceById(productId);
        Path oldPath = Paths.get(productDb.getFilePath());
        try {
            final Path completeFilePath = Paths.get(folderPropertiesConfig.getProduct(), productDb.getId().toString(), image.getOriginalFilename());
            Files.copy(image.getInputStream(), completeFilePath);
            productDb.setFilePath(completeFilePath.toString());
            Files.delete(oldPath);
        } catch (Exception e) {
            log.warn("Smth went wrong with product file", e);
        }
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setDescription(product.getDescription());
        productDb.setQuantity(product.getQuantity());
        return productDb;
    }

    // za kazdym razem usuwa z bazy i potem z cachea
    @CacheEvict(cacheNames = "product", key = "#id")
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
