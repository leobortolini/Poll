package com.ifrs.edu.br.poll.queue;

import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public QueueSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String exchange, String routingKey, VoteDTO order) {
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }
}
