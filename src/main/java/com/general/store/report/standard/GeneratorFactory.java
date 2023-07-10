package com.general.store.report.standard;

import com.general.store.report.model.FileType;
import com.general.store.report.standard.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratorFactory {

    private final List<GeneratorStrategy> generatorStrategies;
    private Map<FileType, GeneratorStrategy> strategyMap;

    @PostConstruct
    void init() {
        strategyMap = generatorStrategies.stream()
                .collect(Collectors.toMap(GeneratorStrategy::getType, Function.identity()));
    }

    public GeneratorStrategy getStrategy(FileType fileType) {
        return strategyMap.get(fileType);
    }
}
