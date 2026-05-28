package com.algasensors.sensor.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.Instant

data class TemperatureMessage(
    @JsonProperty("messageId") val messageId: String,
    @JsonProperty("sensorId") val sensorId: String,
    @JsonProperty("temperature") val temperature: BigDecimal,
    @JsonProperty(value = "unit") val unit: String,
    @JsonProperty("occurredAt") val occurredAt: Instant
)