package com.general.store.report.generic.strategy.language.impl;

import com.general.store.report.model.LanguageName;
import com.general.store.report.generic.strategy.language.LanguageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PolishStrategy implements LanguageStrategy {

    @Override
    public void setLanguage() {
        log.info("Changing application's language to polish");
    }

    @Override
    public LanguageName getType() {
        return LanguageName.POLISH;
    }
}
