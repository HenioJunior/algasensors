package com.algasensors.temperature.processing.infra.messaging.event;

import java.time.Instant;

public record TemperatureProcessedEvent(
        String eventId,
        String sensorId,
        String temperature,
        String unit,
        Instant occurredAt,
        Instant processedAt,
        QualityPayload quality,
        SourcePayload source
) {
    public record QualityPayload(
            boolean valid,
            boolean normalized
    ) {}

    public record SourcePayload(
            String service,
            String topic
    ) {}
}
