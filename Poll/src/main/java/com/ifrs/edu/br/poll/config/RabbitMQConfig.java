package com.ifrs.edu.br.poll.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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
    private String voteDeadLetterQueueName;

    @Value("${exchange.vote.deadletter.name}")
    private String voteDeadLetterExchangeName;

    @Value("${routing.vote.deadletter.key}")
    private String voteDeadLetterRoutingKey;

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
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

        return rabbitTemplate;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(producerJackson2MessageConverter());

        return factory;
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
        return QueueBuilder.durable(voteQueueName).withArgument("x-dead-letter-exchange", voteDeadLetterExchangeName)
                .withArgument("x-dead-letter-routing-key", voteDeadLetterRoutingKey).build();
    }

    @Bean
    public DirectExchange pollExchange() {
        return new DirectExchange(voteExchangeName, true, false);
    }

    @Bean
    public Binding mainExchangeBinding() {
        return BindingBuilder.bind(durableQueue()).to(pollExchange()).with(voteRoutingKey);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(voteDeadLetterQueueName)
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(voteDeadLetterExchangeName);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(voteDeadLetterRoutingKey);
    }
}
