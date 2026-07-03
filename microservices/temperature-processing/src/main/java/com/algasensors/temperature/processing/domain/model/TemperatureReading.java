package com.algasensors.temperature.processing.domain.model;

import com.algasensors.temperature.processing.domain.valueobject.SensorId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class TemperatureReading {

    private final SensorId sensorId;
    private final BigDecimal temperature;
    private final String unit;
    private final Instant occurredAt;

    private TemperatureReading(
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt
    ) {
        this.sensorId = Objects.requireNonNull(sensorId);
        this.temperature = Objects.requireNonNull(temperature);
        this.unit = Objects.requireNonNull(unit);
        this.occurredAt = Objects.requireNonNull(occurredAt);
    }

    public static TemperatureReading of(
            SensorId sensorId,
            BigDecimal temperature,
            String unit,
            Instant occurredAt
    ) {
        return new TemperatureReading(sensorId, temperature, unit, occurredAt);
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
}
