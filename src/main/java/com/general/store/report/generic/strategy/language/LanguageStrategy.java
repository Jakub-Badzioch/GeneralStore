package com.general.store.report.generic.strategy.language;

import com.general.store.report.model.LanguageName;
import com.general.store.report.generic.strategy.GenericStrategy;

public interface LanguageStrategy extends GenericStrategy<LanguageName> {
    void setLanguage();
}