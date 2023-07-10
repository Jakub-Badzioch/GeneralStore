package com.general.store.service;

import com.general.store.model.dao.impl.User;
import com.general.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Test1Service {

    private final UserRepository userRepository;
    private final Test2Service test2Service;

    // najczesceij uzywane propagacje to required i requirednew
  //  @Transactional
    public User getById(Long id){
        test2Service.getById(id);
        return userRepository.getReferenceById(id);
    }
}
