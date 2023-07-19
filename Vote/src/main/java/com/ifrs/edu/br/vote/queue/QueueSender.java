package com.ifrs.edu.br.vote.queue;

import com.ifrs.edu.br.vote.util.dto.EmailNotifyDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueueSender {

    @Value("${exchange.notification.email.name}")
    private String emailNotificationExchangeName;

    @Value("${routing.notification.email.key}")
    private String emailNotificationVoteRoutingKey;

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public QueueSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailNotification(List<EmailNotifyDTO> vote) {
        rabbitTemplate.convertAndSend(emailNotificationExchangeName, emailNotificationVoteRoutingKey, vote);
    }
}
