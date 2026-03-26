package com.algasensors.temperature.monitoring.domain.service;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AlertEvaluationResult {

    private AlertStatus status;
    private TSID sensorId;
    private BigDecimal currentTemperature;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;

    public boolean isIgnored() {
        return status == AlertStatus.IGNORED;
    }

    public boolean isMaxExceeded() {
        return status == AlertStatus.MAX_EXCEEDED;
    }

    public boolean isMinExceeded() {
        return status == AlertStatus.MIN_EXCEEDED;
    }
}
