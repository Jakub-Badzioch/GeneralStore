package com.general.store.repository;

import com.general.store.model.dao.impl.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findDistinctByPriceIsBetween(@NonNull BigDecimal priceStart, @NonNull BigDecimal priceEnd, Pageable pageable);
}
