package com.algasensors.temperature.monitoring.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SensorAlertOutput {
    private TSID id;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
