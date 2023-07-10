package com.general.store.report.standard.strategy.impl;

import com.general.store.report.model.FileType;
import com.general.store.report.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XlsGeneratorStrategy implements GeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generate() {
        log.info("Generating Xls");
        return new byte[0];
    }
}
