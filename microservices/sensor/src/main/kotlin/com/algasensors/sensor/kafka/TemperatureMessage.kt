package com.algasensors.sensor.kafka

import com.fasterxml.jackson.annotation.JsonProperty

data class TemperatureMessage(
    @JsonProperty("sensorId") val sensorId: String,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty("timestamp") val timestamp: String
)