package com.algasensors.sensor.kafka

import io.hypersistence.tsid.TSID
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

        val messageId = TSID.fast().toString()

        val ocurredAt =
            OffsetDateTime.now()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        val message =
            TemperatureMessage(
                messageId,
                sensorId,
                temperature,
                "Celsius",
                ocurredAt
            )

        kafkaTemplate.send(topicName, sensorId, message)
        println("Sent: $message")
    }
}