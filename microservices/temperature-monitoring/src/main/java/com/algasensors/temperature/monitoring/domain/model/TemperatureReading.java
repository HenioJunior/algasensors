package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder
public class TemperatureReading {
    String id;
    SensorId sensorId;
    BigDecimal value;
    OffsetDateTime registeredAt;
}
