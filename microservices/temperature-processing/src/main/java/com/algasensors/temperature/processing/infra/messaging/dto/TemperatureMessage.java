package com.algasensors.temperature.processing.infra.messaging.dto;

public record TemperatureMessage(
        String sensorId,
        Double temperature,
        String unit,
        String timestamp
) {}
