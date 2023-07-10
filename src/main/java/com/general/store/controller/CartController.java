package com.general.store.controller;

import com.general.store.mapper.ProductMapper;
import com.general.store.model.dto.AddProductDTO;
import com.general.store.model.dto.entityanalogue.impl.ProductDTO;
import com.general.store.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v3/carts", produces = MediaType.APPLICATION_JSON_VALUE)

@PreAuthorize("isAuthenticated()")
public class CartController {

    private final CartService cartService;
    private final ProductMapper productMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody AddProductDTO addProductDTO) {
       cartService.addProduct(addProductDTO.getProductId(), addProductDTO.getNeededQuantity());
    }

    @GetMapping
    public List<ProductDTO> getCart() {
        return productMapper.toListDTO(cartService.getAllProductsFromCart());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteProductFromCart(id);
    }
}
