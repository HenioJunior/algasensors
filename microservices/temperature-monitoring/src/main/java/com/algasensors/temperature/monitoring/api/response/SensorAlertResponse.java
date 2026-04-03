package com.algasensors.temperature.monitoring.api.response;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SensorAlertResponse {
    private TSID id;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
}
