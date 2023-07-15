package com.ifrs.edu.br.notification.service;

import com.ifrs.edu.br.poll.util.dto.EmailNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreply@poll.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    @RabbitListener(queues = {"${queue.notification.email.name}"})
    public void sendEmail(EmailNotifyDTO vote) {
        log.info("Sending email about poll " + vote.getIdentifier());
        try {
            sendMessage(vote.getEmail(), "Vote on " + vote.getIdentifier(), "Your vote was computed");
        } catch (Exception ex) {
            log.error("Error sending vote to email queue", ex);
        }
    }

}
