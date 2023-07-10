package com.general.store.report.generic.strategy.report.impl;

import com.general.store.exception.FileCreationException;
import com.general.store.model.dao.impl.Product;
import com.general.store.report.generic.strategy.report.ReportStrategy;
import com.general.store.report.model.FileType;
import com.general.store.repository.ProductRepository;
import com.general.store.service.UserService;
import com.general.store.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class XlsReportStrategy implements ReportStrategy {

    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final UserService userService;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        // WorkbookFactory.create(false) generuje stary excel
        // true zwraca nowy todo daj true, zobacz jaki blad rzuca i obsluz
        try (final Workbook workbook = WorkbookFactory.create(false)) {
            // sheet to zakladka z dodatkowym arkuszem w excelu
            final Sheet sheet = workbook.createSheet("Product Report");
            final Row row = sheet.createRow(0); // w 1 wierszu beda naglowki tego co bedziemy zapisywac dalej
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("createdDate");
            row.createCell(2).setCellValue("lastModifiedDate");
            row.createCell(3).setCellValue("createdBy");
            row.createCell(4).setCellValue("lastModifiedBy");
            row.createCell(5).setCellValue("version");
            row.createCell(6).setCellValue("name");
            row.createCell(7).setCellValue("price");
            row.createCell(8).setCellValue("filePath");
            row.createCell(9).setCellValue("comments");
            row.createCell(10).setCellValue("description");
            row.createCell(11).setCellValue("quantity");
            row.createCell(12).setCellValue("scoreCount");
            row.createCell(13).setCellValue("score");
            final List<Product> products = productRepository.findAll();
            for (int i = 0; i < products.size(); i++) {
                int rowIndex = i + 1;
                final Row rowWithData = sheet.createRow(rowIndex);
                final Product product = products.get(i);
                rowWithData.createCell(0).setCellValue(product.getId());
                rowWithData.createCell(1).setCellValue(product.getCreatedDate());
                rowWithData.createCell(2).setCellValue(product.getLastModifiedDate());
                rowWithData.createCell(3).setCellValue(product.getCreatedBy());
                rowWithData.createCell(4).setCellValue(product.getLastModifiedBy());
                rowWithData.createCell(5).setCellValue(product.getVersion());
                rowWithData.createCell(6).setCellValue(product.getName());
                rowWithData.createCell(7).setCellValue(product.getPrice()!=null? product.getPrice().toString() : "");
                rowWithData.createCell(8).setCellValue(product.getFilePath());
                rowWithData.createCell(9).setCellValue(product.getComments().toString());
                rowWithData.createCell(10).setCellValue(product.getDescription());
                rowWithData.createCell(11).setCellValue(product.getQuantity()!=null? product.getQuantity() : 0);
                rowWithData.createCell(12).setCellValue(product.getScoreCount()!=null? product.getScoreCount() : 0);
                rowWithData.createCell(13).setCellValue(product.getScore()!=null? product.getScore() : 0);
            }
            sheet.setAutoFilter(new CellRangeAddress(0, products.size(), 0, 13));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // garbage collector czysci po uzyciu. nie ogarnia tylko obiektow s[pza bibliotek javowych np Workbook
            // nie uczywaj flush() i close() na ByteArrayOutputStream bo ten obiekt jest inaczej zarzadzany w pamieci
            workbook.write(bos);
            // todo dokon wysylanie maila
            //  todo nanstepnych zjaeciach wielowątkowe programowanie ze springa
            emailService.sendWithAttachment(userService.findCurrentlyLoggedIn().getEmail(),
                    "Generating xls", Collections.emptyMap(), bos.toByteArray(), "products-report");
            return bos.toByteArray();
        } catch (IOException e) {
          // to:  log.warn("Couldn't generate Excel report", e); nie jest potrzeby bo zostanie wyrzucony log z throws
            // zamiast robic na koncu   return new byte[0]; razuc jakiegos runtime'a
            throw new FileCreationException("Couldn't generate Excel report", e);
        }
    }
}
