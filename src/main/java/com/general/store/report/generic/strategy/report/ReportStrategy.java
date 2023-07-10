package com.general.store.report.generic.strategy.report;

import com.general.store.report.generic.strategy.GenericStrategy;
import com.general.store.report.model.FileType;

public interface ReportStrategy extends GenericStrategy<FileType>{
    byte[] generateFile();
}
