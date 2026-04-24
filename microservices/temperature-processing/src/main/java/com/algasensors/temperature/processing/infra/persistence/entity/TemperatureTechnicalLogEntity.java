package com.algasensors.temperature.processing.infra.persistence.entity;

import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "temperature_technical_log")
@Entity
public class TemperatureTechnicalLogEntity {

    @EmbeddedId
    private SensorId sensorId;

    @Column(precision = 10, scale = 2)
    private BigDecimal temperature;

    @Column(length = 10)
    private String unit;

    private Instant occurredAt;

    @Column(nullable = false, updatable = false)
    private Instant loggedAt;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 255)
    private String reason;

    public TemperatureTechnicalLogEntity() {
    }

    private TemperatureTechnicalLogEntity(
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt,
            String status,
            String reason
    ) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.unit = unit;
        this.occurredAt = occurredAt;
        this.status = status;
        this.reason = reason;
    }

    public static TemperatureTechnicalLogEntity received(
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt
    ) {
        return new TemperatureTechnicalLogEntity(
                sensorId,
                temperature,
                unit,
                occurredAt,
                "RECEIVED",
                null
        );
    }

    public static TemperatureTechnicalLogEntity processed(
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt
    ) {
        return new TemperatureTechnicalLogEntity(
                sensorId,
                temperature,
                unit,
                occurredAt,
                "PROCESSED",
                null
        );
    }

    public static TemperatureTechnicalLogEntity discarded(
            SensorId sensorId,
            String reason
    ) {
        return new TemperatureTechnicalLogEntity(
                sensorId,
                null,
                null,
                Instant.now(),
                "DISCARDED",
                reason
        );
    }


    @PrePersist
    void prePersist() {
        if (loggedAt == null) {
            loggedAt = Instant.now();
        }
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

    public Instant getLoggedAt() {
        return loggedAt;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

}
