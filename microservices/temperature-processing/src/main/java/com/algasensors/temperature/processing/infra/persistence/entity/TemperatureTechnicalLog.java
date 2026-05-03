package com.algasensors.temperature.processing.infra.persistence.entity;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.infra.persistence.gateway.TemperatureTechnicalLogStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class TemperatureTechnicalLog {

    private String id;
    private String rawMessageId;
    private String processedEventId;
    private SensorId sensorId;
    private BigDecimal temperature;
    private String unit;
    private Instant occurredAt;
    private Instant receivedAt;
    private Instant processedAt;
    private TemperatureTechnicalLogStatus status;
    private String errorMessage;

    public static TemperatureTechnicalLog received(String rawMessageId, TemperatureReading reading) {
        TemperatureTechnicalLog log = new TemperatureTechnicalLog();
        log.rawMessageId = rawMessageId;
        log.sensorId = reading.getSensorId();
        log.temperature = reading.getTemperature();
        log.unit = reading.getUnit();
        log.occurredAt = reading.getOccurredAt();
        log.receivedAt = Instant.now();
        log.status = TemperatureTechnicalLogStatus.RECEIVED;
        return log;
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

    public void setProcessedEventId(String processedEventId) {
        this.processedEventId = processedEventId;
    }

    public void setSensorId(SensorId sensorId) {
        this.sensorId = sensorId;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public void setStatus(TemperatureTechnicalLogStatus status) {
        this.status = status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRawMessageId(String rawMessageId) {
        this.rawMessageId = rawMessageId;
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

    public String getId() {
        return id;
    }
}
