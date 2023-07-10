package com.general.store.controller;

import com.general.store.model.dto.OrderCartDTO;
import com.general.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void orderCart(@RequestBody OrderCartDTO orderCartDTO) {
        orderService.orderCart(orderCartDTO.getAddressId(), orderCartDTO.getDiscountCode());
    }
}
