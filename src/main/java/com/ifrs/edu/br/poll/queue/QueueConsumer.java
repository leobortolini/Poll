package com.ifrs.edu.br.poll.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Message message) {
        LOGGER.info("queueConsumer() - " + message.toString());
    }
}
