package com.algasensors.sensor.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class TemperatureProducer(
    private val kafkaTemplate: KafkaTemplate<String, TemperatureMessage>
) {

    @Value("\${kafka.topic.name}")
    lateinit var topicName: String

    fun sendTemperature(sensorId: String, temperature: Double) {
        val timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val temperatureMessage = TemperatureMessage(sensorId, temperature, timestamp)

        // Enviando o JSON para o Kafka
        kafkaTemplate.send(topicName, sensorId, temperatureMessage)
        println("Sent: $temperatureMessage")
    }
}