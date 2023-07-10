package com.general.store.service.email;

import java.util.Map;

public interface EmailService {
    void sendWithAttachment(String toEmail, String templateName, Map<String, Object> variables,
                            byte[] file, String fileName);
}
