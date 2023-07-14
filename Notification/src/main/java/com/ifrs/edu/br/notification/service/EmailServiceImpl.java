package com.ifrs.edu.br.notification.service;

import com.ifrs.edu.br.poll.util.dto.VoteDTO;
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
    public void sendEmail(VoteDTO vote) {
        log.info("Sending email about poll " + vote.getIdentifier());
        sendMessage("user@email.com", "Vote on " + vote.getIdentifier(), "Your vote on option " + vote.getVoteRequest().getOptionId() + " was computed");
    }

}
