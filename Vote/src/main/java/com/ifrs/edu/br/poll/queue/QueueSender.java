package com.ifrs.edu.br.poll.queue;

import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Value("${exchange.vote.name}")
    private String voteExchangeName;

    @Value("${routing.vote.key}")
    private String voteRoutingKey;

    @Value("${exchange.notification.email.name}")
    private String emailNotificationExchangeName;

    @Value("${routing.notification.email.key}")
    private String emailNotificationVoteRoutingKey;

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public QueueSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVote(VoteDTO vote) {
        rabbitTemplate.convertAndSend(voteExchangeName, voteRoutingKey, vote);
    }

    public void sendEmailNotification(VoteDTO vote) {
        rabbitTemplate.convertAndSend(emailNotificationExchangeName, emailNotificationVoteRoutingKey, vote);
    }
}
