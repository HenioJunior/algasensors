package com.algasensors.sensor.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

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
        val configs = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java  // Usando JsonSerializer para o valor
        )
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, TemperatureMessage> {
        return KafkaTemplate(producerFactory())
    }
}