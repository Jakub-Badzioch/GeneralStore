package com.general.store.controller;

import com.general.store.report.generic.GenericFactory;
import com.general.store.report.generic.strategy.report.ReportStrategy;
import com.general.store.report.model.FileType;
import com.general.store.report.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/files", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class FileController {

    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, ReportStrategy> genericFactory;

    @GetMapping
    public void generateFile(@RequestParam FileType fileType) {
        generatorFactory.getStrategy(fileType).generate();
    }

    // todo na zajeciach napiszmy testy do tego
    //  moglbym napisac 2 testy: 1 happypath i jeden gdzie enuma nie ma
    @GetMapping("/generic")
    public ResponseEntity<byte[]> generateFileGeneric(@RequestParam FileType fileType) {
        final byte[] file = genericFactory.getStrategy(fileType).generateFile();
        final HttpHeaders httpHeaders = new HttpHeaders();
        // mowimy klientowi ze bedziemy zwracać plik
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // jak duzy plik zwracamy, dzieki temu przegladarka bedzie miala progres bar przy sciaganiu
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
        // nazwa pliku
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report."
                + fileType.toString().toLowerCase(Locale.ROOT));
        // sam plik jedyne sensowne miejsce do uzycia responseEntity
        // uzywasz re tylko jak musisz zwrocic jakies custromowe headery lub plik
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }
}
