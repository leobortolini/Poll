package com.ifrs.edu.br.notification.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendMessage(SimpleMailMessage[] emailsTosEnd);
}
