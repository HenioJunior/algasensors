package com.algasensors.temperature.monitoring.api.model;

import com.algasensors.temperature.monitoring.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureLogData {
private String id;
private SensorId sensorId;
private OffsetDateTime registeredAt;
private BigDecimal value;
}
