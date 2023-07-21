package com.ifrs.edu.br.notification.service;

import com.ifrs.edu.br.notification.util.dto.EmailNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(SimpleMailMessage[] emailsTosEnd) {
        javaMailSender.send(emailsTosEnd);
    }

    @RabbitListener(queues = {"${queue.notification.email.name}"})
    public void sendEmail(List<EmailNotifyDTO> notifications) {
        log.info("sendEmail - start() with " + notifications.size() + " notifications to sent");
        List<SimpleMailMessage> emailNotifications = new ArrayList<>(notifications.size());

        for (EmailNotifyDTO notifyDTO : notifications) {
            SimpleMailMessage message = getSimpleMailMessage(notifyDTO);

            emailNotifications.add(message);
        }

        sendMessage(emailNotifications.toArray(SimpleMailMessage[]::new));
    }

    private static SimpleMailMessage getSimpleMailMessage(EmailNotifyDTO notifyDTO) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreply@poll.com");
        message.setTo(notifyDTO.email());
        message.setSubject("Vote on " + notifyDTO.identifier());
        message.setText("Your vote was computed");

        return message;
    }
}
