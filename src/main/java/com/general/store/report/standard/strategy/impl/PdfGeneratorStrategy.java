package com.general.store.report.standard.strategy.impl;

import com.general.store.report.model.FileType;
import com.general.store.report.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfGeneratorStrategy implements GeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generate() {
        log.info("Generating pdf");
        return new byte[0];
    }
}
