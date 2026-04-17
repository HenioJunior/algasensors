package com.algasensors.temperature.processing.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class TemperatureLogResponse {
private SensorId sensorId;
private OffsetDateTime registeredAt;
private Double value;
}
