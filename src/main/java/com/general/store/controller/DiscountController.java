package com.general.store.controller;

import com.general.store.mapper.DiscountMapper;
import com.general.store.model.dto.entityanalogue.impl.DiscountDTO;
import com.general.store.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDiscount(@RequestBody DiscountDTO discountDTO) {
        discountService.create(discountMapper.toEntity(discountDTO));
    }

    @GetMapping("/{id}")
    public DiscountDTO getDiscountById(@PathVariable Long id) {
        return discountMapper.toDto(discountService.getById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DiscountDTO updateDiscount(@PathVariable Long id, @RequestBody DiscountDTO discountDTO) {
        return discountMapper.toDto(discountService.update(discountMapper.toEntity(discountDTO), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscount(@PathVariable Long id) {
        discountService.delete(id);
    }
}
