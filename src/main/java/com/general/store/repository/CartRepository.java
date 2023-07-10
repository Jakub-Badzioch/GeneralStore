package com.general.store.repository;

import com.general.store.model.dao.impl.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(@NonNull Long userId);
    Optional<Cart> findByProductIdAndUserId(@NonNull Long productId, @NonNull Long userId);
    void deleteByUserId(@NonNull Long userId);
}
