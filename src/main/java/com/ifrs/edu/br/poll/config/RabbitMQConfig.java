package com.ifrs.edu.br.poll.config;

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

    @Value("${queue.name}")
    private String queueName;

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routing.key}")
    private String routingKey;

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
    public Queue durableQueue() {
        return QueueBuilder.durable(queueName).withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    public TopicExchange mainExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Binding mainExchangeBinding() {
        return BindingBuilder.bind(durableQueue()).to(mainExchange()).with(routingKey);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dead-letter-queue")
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("deadLetter");
    }
}
