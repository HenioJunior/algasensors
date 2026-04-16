package com.algasensors.sensor.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@EnableKafka
class KafkaProducerConfig {

    @Value("\${kafka.topic.name}")
    lateinit var topicName: String

    @Bean
    fun temperatureTopic(): NewTopic {
        return TopicBuilder.name(topicName)
            .partitions(3)
            .replicas(1)
            .build()
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, TemperatureMessage> {
        // Ao não passar um Map, o Spring usa as configurações do application.yaml
        return DefaultKafkaProducerFactory(emptyMap())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, TemperatureMessage> {
        return KafkaTemplate(producerFactory())
    }
}