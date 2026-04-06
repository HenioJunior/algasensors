package com.algasensors.temperature.monitoring.api.response;

import com.algasensors.temperature.monitoring.domain.model.SensorId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SensorAlertResponse {
    private SensorId id;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
