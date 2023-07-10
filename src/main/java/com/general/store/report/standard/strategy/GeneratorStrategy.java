package com.general.store.report.standard.strategy;

import com.general.store.report.model.FileType;

public interface GeneratorStrategy {
    FileType getType();
    byte[] generate();
}
