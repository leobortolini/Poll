package com.ifrs.edu.br.poll.queue;

import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Value("${exchange.vote.name}")
    private String voteExchangeName;

    @Value("${routing.vote.key}")
    private String voteRoutingKey;

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public QueueSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVote(VoteDTO vote) {
        rabbitTemplate.convertAndSend(voteExchangeName, voteRoutingKey, vote);
    }
}
