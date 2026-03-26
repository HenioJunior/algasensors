package com.algasensors.temperature.monitoring.infra.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
    public static final String TEMPERATURE_ALERT_QUEUE = "temperature-alert.queue";
    public static final String TEMPERATURE_ALERT_EXCHANGE = "temperature-alert.exchange";
    public static final String TEMPERATURE_ALERT_ROUTING_KEY = "temperature-alert.routing-key";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE).build();
    }

    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("temperature-processing.temperature-received.v1.e").build();
    }

    @Bean
    public Binding bindingProcessTemperature() {
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }

    @Bean
    public Queue temperatureAlertQueue() {
        return QueueBuilder.durable(TEMPERATURE_ALERT_QUEUE).build();
    }

    @Bean
    public TopicExchange temperatureAlertExchange() {
        return ExchangeBuilder
                .topicExchange(TEMPERATURE_ALERT_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Binding temperatureAlertBinding() {
        return BindingBuilder
                .bind(temperatureAlertQueue())
                .to(temperatureAlertExchange())
                .with(TEMPERATURE_ALERT_ROUTING_KEY);
    }
}
