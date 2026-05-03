package com.algasensors.sensor.kafka

import com.fasterxml.jackson.annotation.JsonProperty

data class TemperatureMessage(
    @JsonProperty("messageId") val messageId: String,
    @JsonProperty("sensorId") val sensorId: String,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty(value = "unit") val unit: String,
    @JsonProperty("occurredAt") val occurredAt: String
)