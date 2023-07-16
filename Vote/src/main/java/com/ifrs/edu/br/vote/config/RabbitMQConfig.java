package com.ifrs.edu.br.vote.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${queue.vote.name}")
    private String voteQueueName;

    @Value("${exchange.vote.name}")
    private String voteExchangeName;

    @Value("${routing.vote.key}")
    private String voteRoutingKey;

    @Value("${queue.vote.deadletter.name}")
    private String deadLetterQueueName;

    @Value("${exchange.vote.deadletter.name}")
    private String deadLetterExchangeName;

    @Value("${routing.vote.deadletter.key}")
    private String deadLetterRoutingKey;

    @Value("${queue.notification.email.name}")
    private String notificationEmailQueueName;

    @Value("${exchange.notification.email.name}")
    private String notificationEmailExchangeName;

    @Value("${routing.notification.email.key}")
    private String notificationEmailRoutingKey;

    @Value("${exchange.notification.email.deadletter.name}")
    private String notificationEmailDeadLetterExchangeName;

    @Value("${routing.notification.email.deadletter.key}")
    private String notificationEmailDeadLetterRoutingKey;

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
    public RabbitTemplate rabbitTemplate() {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Queue durableQueue() {
        return QueueBuilder.durable(voteQueueName).withArgument("x-dead-letter-exchange", deadLetterExchangeName)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey).build();
    }

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange(voteExchangeName, true, false);
    }

    @Bean
    public Binding mainExchangeBinding() {
        return BindingBuilder.bind(durableQueue()).to(mainExchange()).with(voteRoutingKey);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueueName).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchangeName);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(deadLetterRoutingKey);
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
