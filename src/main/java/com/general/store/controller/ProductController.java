package com.general.store.controller;

import com.general.store.mapper.ProductMapper;
import com.general.store.model.dto.entityanalogue.impl.ProductDTO;
import com.general.store.service.ProductService;
import com.general.store.validator.IsItJpgIn180x80px;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v3/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public Page<ProductDTO> filterProducts(@RequestParam(required = false) BigDecimal priceStart,
                                           @RequestParam(required = false) BigDecimal priceEnd,
                                           @RequestParam int page, @RequestParam int size) {
        return productService.filter(priceStart, priceEnd, PageRequest.of(page, size))
                .map(productMapper::toDto);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // todo dodaj walidacje na produkt
    public ProductDTO addProductToShop(@RequestPart ProductDTO productDTO,
                                       @RequestPart @IsItJpgIn180x80px MultipartFile image) {
        return productMapper.toDto(productService.create(productMapper.toEntity(productDTO), image));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductDTO updateProductInShop(@PathVariable Long id, @RequestPart ProductDTO productDTO,
                                          @Nullable @RequestPart @IsItJpgIn180x80px MultipartFile image) {
        return productMapper.toDto(productService.update(productMapper.toEntity(productDTO), image, id));
    }
}



/// todo obsluz ten nullpointer