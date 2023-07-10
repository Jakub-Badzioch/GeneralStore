package com.general.store.service;

import com.general.store.model.dao.impl.Discount;
import com.general.store.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public Discount getById(Long id) {
        return discountRepository.getReferenceById(id);
    }

    public Discount getByDiscountCode(String discountCode) {
        return discountRepository.findByCodeAndIsAvailableIsTrue(discountCode).orElseThrow(EntityNotFoundException::new);
    }

    public void create(Discount discount) {
        discountRepository.save(discount);
    }

    public Discount update(Discount discount, Long id) {
        final Discount discountDb = discountRepository.getReferenceById(id);
        discountDb.setCode(discount.getCode());
        discountDb.setIsAvailable(discount.getIsAvailable());
        discountDb.setDiscountsPercentage(discount.getDiscountsPercentage());
        discountDb.setValidDate(discount.getValidDate());
        return discountDb;
    }

    public void delete(Long id) {
        discountRepository.deleteById(id);
    }
}
