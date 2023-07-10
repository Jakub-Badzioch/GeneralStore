package com.general.store.repository;

import com.general.store.model.dao.impl.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCodeAndIsAvailableIsTrue(@NonNull String code);
}
