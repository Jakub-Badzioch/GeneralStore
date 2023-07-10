package com.general.store.repository;

import com.general.store.model.dao.impl.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @NonNull
    Optional<Role> findByName(@NonNull String name);
}
