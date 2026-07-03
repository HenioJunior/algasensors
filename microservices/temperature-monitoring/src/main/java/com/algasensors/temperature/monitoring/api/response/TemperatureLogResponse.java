package com.algasensors.temperature.monitoring.api.response;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@Builder
public class TemperatureLogResponse {
private String id;
private SensorId sensorId;
private OffsetDateTime registeredAt;
private BigDecimal value;

}
