package com.general.store.service;

import com.general.store.model.dao.impl.Cart;
import com.general.store.model.dao.impl.Product;
import com.general.store.model.dao.impl.User;
import com.general.store.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public Cart addProduct(Long id, Long neededQuantity) {
        Product product = productService.getById(id);
        Long quantity = product.getQuantity();
        User user = userService.findCurrentlyLoggedIn();
        if (quantity > neededQuantity) quantity = neededQuantity;
        Cart cart = cartRepository.findByProductIdAndUserId(id, user.getId()).orElseGet(() -> Cart.builder()
                .product(product)
                .user(user)
                .build());
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public List<Product> getAllProductsFromCart() {
        return getAllCarts(userService.findCurrentlyLoggedIn().getId()).stream()
                .map(cart -> {
                    Product product = cart.getProduct();
                    product.setQuantity(cart.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }

    public void deleteProductFromCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void deleteWholeCart() {
        cartRepository.deleteByUserId(userService.findCurrentlyLoggedIn().getId());
    }

    public List<Cart> getAllCarts(Long id) {
        return cartRepository.findByUserId(id);
    }
}


