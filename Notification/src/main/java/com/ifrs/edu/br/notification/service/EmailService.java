package com.ifrs.edu.br.notification.service;

public interface EmailService {
    void sendMessage(String to, String subject, String content);
}
