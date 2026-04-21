package com.algasensors.temperature.processing.infra.messaging.dto;

import java.time.Instant;

public record TemperatureMessage(
        String messageId,
        String sensorId,
        Double temperature,
        String unit,
        Instant occurredAt
) {}
