package com.algasensors.temperature.processing.infra.messaging.dto;

import java.time.Instant;

public record TemperatureProcessedEventMessage(
        String eventId,
        String sensorId,
        String temperature,
        String unit,
        Instant occurredAt,
        Instant processedAt,
        Quality quality,
        Source source
) {
    public record Quality(boolean valid, boolean normalized) {}
    public record Source(String service, String topic) {}
}
