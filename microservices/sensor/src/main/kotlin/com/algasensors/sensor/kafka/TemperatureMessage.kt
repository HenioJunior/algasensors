package com.algasensors.sensor.kafka

import com.fasterxml.jackson.annotation.JsonProperty

data class TemperatureMessage(
    @JsonProperty("sensorId") val sensorId: String,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty(value = "unit") val unit: String,
    @JsonProperty("timestamp") val timestamp: String
)