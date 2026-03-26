package com.algasensors.temperature.monitoring.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SensorAlertInput {
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
