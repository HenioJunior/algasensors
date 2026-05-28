package com.algasensors.temperature.monitoring.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SensorAlertRequest {

    @DecimalMin("0.0")
    private BigDecimal minTemperature;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal warningMinTemperature;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal warningMaxTemperature;

    @DecimalMin("0.0")
    private BigDecimal maxTemperature;

}
