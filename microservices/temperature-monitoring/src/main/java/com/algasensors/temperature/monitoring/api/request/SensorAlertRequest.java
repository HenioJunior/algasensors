package com.algasensors.temperature.monitoring.api.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class SensorAlertRequest {

    @DecimalMin("0.0")
    private BigDecimal minTemperature;

    @DecimalMin("0.0")
    private BigDecimal maxTemperature;

}
