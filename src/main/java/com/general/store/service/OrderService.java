package com.general.store.service;

import com.general.store.model.OrdersStatus;
import com.general.store.model.dao.impl.*;
import com.general.store.repository.OrderDetailsRepository;
import com.general.store.repository.OrderEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;
    private final DiscountService discountService;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderEntryRepository orderEntryRepository;
    private final ProductService productService;

    public void orderCart(Long addressId, String discountCode) {
        final User user = userService.findCurrentlyLoggedIn();
        final Address address = addressService.getById(addressId);
        Discount discount = discountService.getByDiscountCode(discountCode);
        List<Cart> carts = cartService.getAllCarts(user.getId());
        OrderDetails orderDetails = OrderDetails.builder()
                .address(address)
                .discount(discount)
                .user(user)
                .status(OrdersStatus.CREATED)
                .build();
        List<OrderEntry> orderEntries = carts.stream().map(cart -> OrderEntry.builder()
                        .product(cart.getProduct())
                        .quantity(cart.getQuantity())
                        .unitPrice(cart.getProduct().getPrice())
                        .orderDetails(orderDetails)
                        .build())
                        .collect(Collectors.toList());
        orderDetails.setTotalPrice(orderEntries.stream()
                .map(orderEntry -> orderEntry.getUnitPrice().multiply(BigDecimal.valueOf(orderEntry.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderDetailsRepository.save(orderDetails);
        final List<OrderEntry> entries = orderEntryRepository.saveAll(orderEntries);
        entries.forEach(orderEntry -> {
            final Product product = orderEntry.getProduct();
            product.setQuantity(product.getQuantity() - orderEntry.getQuantity());
            productService.replace(product);
        });
        cartService.deleteWholeCart();
    }
}
