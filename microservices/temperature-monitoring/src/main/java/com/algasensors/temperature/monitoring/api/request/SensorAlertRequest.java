package com.algasensors.temperature.monitoring.api.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Getter
public class SensorAlertRequest {
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal minTemperature;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal maxTemperature;

}
