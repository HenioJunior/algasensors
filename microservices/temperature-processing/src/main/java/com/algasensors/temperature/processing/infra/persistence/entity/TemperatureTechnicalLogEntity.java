package com.algasensors.temperature.processing.infra.persistence.entity;

import com.algasensors.temperature.processing.domain.model.TemperatureTechnicalLog;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.infra.persistence.gateway.TemperatureTechnicalLogStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "temperature_technical_log")
@Entity
public class TemperatureTechnicalLogEntity {

    @Id
    private String id;

    @Column(name = "raw_message_id")
    private String rawMessageId;

    @Column(name = "processed_event_id")
    private String processedEventId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", nullable = false))
    private SensorId sensorId;

    @Column(name = "temperature", nullable = false, precision = 10, scale = 2)
    private BigDecimal temperature;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TemperatureTechnicalLogStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRawMessageId() {
        return rawMessageId;
    }

    public void setRawMessageId(String rawMessageId) {
        this.rawMessageId = rawMessageId;
    }

    public String getProcessedEventId() {
        return processedEventId;
    }

    public void setProcessedEventId(String processedEventId) {
        this.processedEventId = processedEventId;
    }

    public SensorId getSensorId() {
        return sensorId;
    }

    public void setSensorId(SensorId sensorId) {
        this.sensorId = sensorId;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public TemperatureTechnicalLogStatus getStatus() {
        return status;
    }

    public void setStatus(TemperatureTechnicalLogStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static TemperatureTechnicalLogEntity fromDomain(TemperatureTechnicalLog domain) {
        TemperatureTechnicalLogEntity entity = new TemperatureTechnicalLogEntity();
        entity.id = domain.getId();
        entity.rawMessageId = domain.getRawMessageId();
        entity.processedEventId = domain.getProcessedEventId();
        entity.sensorId = domain.getSensorId();
        entity.temperature = domain.getTemperature();
        entity.unit = domain.getUnit();
        entity.occurredAt = domain.getOccurredAt();
        entity.receivedAt = domain.getReceivedAt();
        entity.processedAt = domain.getProcessedAt();
        entity.status = domain.getStatus();
        entity.errorMessage = domain.getErrorMessage();
        return entity;
    }

    public TemperatureTechnicalLog toDomain() {
        return TemperatureTechnicalLog.withAll(
                this.id,
                this.rawMessageId,
                this.processedEventId,
                this.sensorId,
                this.temperature,
                this.unit,
                this.occurredAt,
                this.receivedAt,
                this.processedAt,
                this.status,
                this.errorMessage
        );
    }
}
