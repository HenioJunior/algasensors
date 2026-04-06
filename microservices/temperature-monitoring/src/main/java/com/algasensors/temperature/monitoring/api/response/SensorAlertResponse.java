package com.algasensors.temperature.monitoring.api.response;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SensorAlertResponse {
    private SensorId sensorId;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
