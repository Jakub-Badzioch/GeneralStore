package com.general.store.report.generic.strategy.report.impl;

import com.general.store.model.dao.impl.Product;
import com.general.store.report.generic.strategy.report.ReportStrategy;
import com.general.store.report.model.FileType;
import com.general.store.repository.ProductRepository;
import com.lowagie.text.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfReportStrategy implements ReportStrategy {

    private final ProductRepository productRepository;

    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generateFile() {
        // todo wygeneruj tabelke, wpisac dane i wyeksportowac
        log.info("Generating pdf");
        Document document = new Document(PageSize.A4);
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Products with quantities", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        Paragraph paragraph2 = new Paragraph(getProductNamesWithQuantities(), fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph);
        document.add(paragraph2);
        document.close();

        return null;
    }

    private String getProductNamesWithQuantities(){
        final List<Product> products = productRepository.findAll();
        final StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.getName()).append(" ").append(product.getQuantity()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}