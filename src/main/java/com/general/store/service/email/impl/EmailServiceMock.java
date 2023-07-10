package com.general.store.service.email.impl;


import com.general.store.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile("!mail")
@Slf4j
public class EmailServiceMock implements EmailService {
    @Override
    public void sendWithAttachment(String toEmail, String templateName, Map<String, Object> variables,
                                   byte[] file, String fileName) {
        log.info(toEmail);
    }
}

