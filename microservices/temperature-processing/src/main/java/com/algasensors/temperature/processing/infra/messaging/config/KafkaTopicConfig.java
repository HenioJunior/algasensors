package com.algasensors.temperature.processing.infra.messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topics.processed-reading}")
    private String processedTopicName;

    @Value("${app.kafka.topics.raw-reading}")
    private String rawTopicName;

    @Bean
    public NewTopic processedTemperatureTopic() {
        return TopicBuilder.name(processedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic rawTemperatureTopic() {
        return TopicBuilder.name(rawTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
