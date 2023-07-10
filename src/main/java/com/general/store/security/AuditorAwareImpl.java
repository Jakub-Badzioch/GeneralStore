package com.general.store.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // tutaj trzeba zwrocic optionala ktory ma albo nieam wartosci w zaleznosci od tego czy uzytkownik jest lub niejest a zalogowany
        return Optional.ofNullable(SecurityUtils.getUserName());
    }
}
