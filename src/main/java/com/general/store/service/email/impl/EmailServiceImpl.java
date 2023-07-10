package com.general.store.service.email.impl;

import com.general.store.model.dao.impl.Template;
import com.general.store.service.TemplateService;
import com.general.store.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("mail")
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateService templateService;
    private final ITemplateEngine iTemplateEngine;
    // todo jak wysłać tego excela mailem

    @Override
    // defaultowo nie dsziala trzeba dodac enable async w main
    // wykonuje metode w osobnym wątku
    @Async
    public void sendWithAttachment(String toEmail, String templateName, Map<String, Object> variables,
                                   byte[] file, String fileName) {
        final Template template = templateService.getByName(templateName);
        // variables w konstuktorze new Context doslownie laduja zmienne templatu(klucz=nazwa templatu, wartosc= wartosc ktora zostanie podstawiona pod zmienna w templatcie)
        final String body = iTemplateEngine.process(template.getBody(), new Context(Locale.getDefault(), variables));
        javaMailSender.send(mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            if (file != null && fileName != null) {
                helper.addAttachment(fileName, new ByteArrayResource(file));
            }
            helper.setTo(toEmail);
            helper.setSubject(template.getSubject());
            // robimy to zamiast template.getBody zeby mozna bylo dodac dodatkowe rzeczy w tekscie templatu
            // true oznacza ze bedziemy wysylac htmla i to sie ladnie sformatuje dzieki temu
            helper.setText(body, true);
        });
    }
}
