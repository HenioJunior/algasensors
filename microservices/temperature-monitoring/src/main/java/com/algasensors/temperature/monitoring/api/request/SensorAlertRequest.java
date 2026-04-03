package com.algasensors.temperature.monitoring.api.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SensorAlertRequest {
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
