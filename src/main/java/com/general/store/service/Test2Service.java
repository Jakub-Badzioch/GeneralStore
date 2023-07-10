package com.general.store.service;

import com.general.store.model.dao.impl.Product;
import com.general.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Test2Service {

    private final ProductRepository productRepository;

    // jest tka sama jak required
    // Isolation.default rozni sie w zaleznosci od bazy danych
    // w isolation chodzi o to jak sa pobierane dane
    // izolacja rozwiazuje problem dirty read czyli czytanie niezakommitowanych danych
    @Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_COMMITTED)
    public Product getById(Long id){
        return productRepository.getReferenceById(id);

        // Isolation.REPEATABLE_READ - nie pozwala na czytanie wierszy ktore maja niezakomitowane zmiany

        // optimistic locking

        // defautlowe izolacje w bazach danych:
        // - repeatable read zapobiega dirtyread i nonreapeatable read
        // (nie ogarnia tylko phantom readow)
        // - oracle & postgres zapobiegaja tylko dirty readowi
    }
}
