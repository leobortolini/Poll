package com.ifrs.edu.br.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${queue.notification.email.deadletter.name}")
    private String notificationEmailDeadLetterQueueName;

    @Value("${exchange.notification.email.deadletter.name}")
    private String notificationEmailDeadLetterExchangeName;

    @Value("${routing.notification.email.deadletter.key}")
    private String notificationEmailDeadLetterRoutingKey;

    @Value("${queue.notification.email.name}")
    private String notificationEmailQueueName;

    @Value("${exchange.notification.email.name}")
    private String notificationEmailExchangeName;

    @Value("${routing.notification.email.key}")
    private String notificationEmailRoutingKey;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);

        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(notificationEmailDeadLetterQueueName).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(notificationEmailDeadLetterExchangeName);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(notificationEmailDeadLetterRoutingKey);
    }

    @Bean
    public Queue queueNotificationEmailQueue() {
        return QueueBuilder.durable(notificationEmailQueueName).withArgument("x-dead-letter-exchange", notificationEmailDeadLetterExchangeName)
                .withArgument("x-dead-letter-routing-key", notificationEmailDeadLetterRoutingKey).build();
    }

    @Bean
    public DirectExchange notificationEmailExchange() {
        return new DirectExchange(notificationEmailExchangeName, true, false);
    }

    @Bean
    public Binding notificationEmailBinding() {
        return BindingBuilder.bind(queueNotificationEmailQueue()).to(notificationEmailExchange()).with(notificationEmailRoutingKey);
    }
}
