package com.algasensors.sensor.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class TemperatureProducer(
    private val kafkaTemplate: KafkaTemplate<String, TemperatureMessage>,
    @Value("\${app.kafka.topics.raw-reading}")
    private val topicName: String
) {

    fun sendTemperature(sensorId: String, temperature: Double) {

        val timestamp =
            OffsetDateTime.now()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        val message =
            TemperatureMessage(
                sensorId,
                temperature,
                timestamp
            )

        kafkaTemplate.send(topicName, sensorId, message)
        println("Sent: $message")
    }
}