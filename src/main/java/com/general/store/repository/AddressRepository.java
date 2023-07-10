package com.general.store.repository;

import com.general.store.model.dao.impl.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
