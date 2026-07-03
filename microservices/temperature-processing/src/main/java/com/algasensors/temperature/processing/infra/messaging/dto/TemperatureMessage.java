package com.algasensors.temperature.processing.infra.messaging.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TemperatureMessage(
        String messageId,
        String sensorId,
        BigDecimal temperature,
        String unit,
        Instant occurredAt
) {}
