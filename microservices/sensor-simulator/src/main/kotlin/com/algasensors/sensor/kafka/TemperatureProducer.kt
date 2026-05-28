package com.algasensors.sensor.kafka

import com.algasensors.sensor.dto.TemperatureMessage
import io.hypersistence.tsid.TSID
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class TemperatureProducer(
    private val kafkaTemplate: KafkaTemplate<String, TemperatureMessage>,
    @Value("\${app.kafka.topics.raw-reading}")
    private val topicName: String
) {
    private val logger = LoggerFactory.getLogger(TemperatureProducer::class.java)

    fun sendTemperature(message: TemperatureMessage) {
        kafkaTemplate.send(topicName, message.sensorId, message)
        logger.info("Sent: {}", message)
    }
}