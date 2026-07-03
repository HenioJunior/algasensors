package com.algasensors.temperature.monitoring.api.response;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SensorAlertResponse {
    private SensorId id;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
