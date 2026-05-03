package com.algasensors.temperature.processing.domain.model;

import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.infra.persistence.gateway.TemperatureTechnicalLogStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class TemperatureTechnicalLog {

    private String id;
    private final String rawMessageId;
    private String processedEventId;
    private final SensorId sensorId;
    private final BigDecimal temperature;
    private final String unit;
    private final Instant occurredAt;
    private final Instant receivedAt;
    private Instant processedAt;
    private TemperatureTechnicalLogStatus status;
    private String errorMessage;

    private TemperatureTechnicalLog(
            String id,
            String rawMessageId,
            String processedEventId,
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt,
            Instant receivedAt,
            Instant processedAt,
            TemperatureTechnicalLogStatus status,
            String errorMessage
    ) {
        this.id = id;
        this.rawMessageId = rawMessageId;
        this.processedEventId = processedEventId;
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.unit = unit;
        this.occurredAt = occurredAt;
        this.receivedAt = receivedAt;
        this.processedAt = processedAt;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static TemperatureTechnicalLog received(String rawMessageId, TemperatureReading reading) {
        return new TemperatureTechnicalLog(
                null,
                rawMessageId,
                null,
                reading.getSensorId(),
                reading.getTemperature(),
                reading.getUnit(),
                reading.getOccurredAt(),
                Instant.now(),
                null,
                TemperatureTechnicalLogStatus.RECEIVED,
                null
        );
    }

    public static TemperatureTechnicalLog withAll(
            String id,
            String rawMessageId,
            String processedEventId,
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt,
            Instant receivedAt,
            Instant processedAt,
            TemperatureTechnicalLogStatus status,
            String errorMessage
    ) {
        return new TemperatureTechnicalLog(
                id,
                rawMessageId,
                processedEventId,
                sensorId,
                temperature,
                unit,
                occurredAt,
                receivedAt,
                processedAt,
                status,
                errorMessage
        );
    }

    public void setId(String id) {
        this.id = id;
    }

    public void markAsProcessed(String processedEventId, Instant processedAt) {
        this.processedEventId = processedEventId;
        this.processedAt = processedAt;
        this.status = TemperatureTechnicalLogStatus.PROCESSED;
    }

    public void markAsFailed(String errorMessage) {
        this.errorMessage = errorMessage;
        this.status = TemperatureTechnicalLogStatus.FAILED;
    }

    public String getId() {
        return id;
    }

    public String getRawMessageId() {
        return rawMessageId;
    }

    public String getProcessedEventId() {
        return processedEventId;
    }

    public SensorId getSensorId() {
        return sensorId;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public String getUnit() {
        return unit;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public TemperatureTechnicalLogStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
