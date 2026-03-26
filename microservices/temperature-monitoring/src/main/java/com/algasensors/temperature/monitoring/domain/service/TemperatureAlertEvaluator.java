package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TemperatureAlertEvaluator {

    public AlertEvaluationResult evaluate(TemperatureLogData temperatureLogData,
                                          Optional<SensorAlert> sensorAlertOptional) {

        BigDecimal value = temperatureLogData.getValue();

        if (sensorAlertOptional.isEmpty()) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.IGNORED)
                    .sensorId(temperatureLogData.getSensorId())
                    .currentTemperature(value)
                    .build();
        }

        SensorAlert sensorAlert = sensorAlertOptional.get();

        if (sensorAlert.getMaxTemperature() != null
                && value.compareTo(sensorAlert.getMaxTemperature()) >= 0) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.MAX_EXCEEDED)
                    .sensorId(temperatureLogData.getSensorId())
                    .currentTemperature(value)
                    .maxTemperature(sensorAlert.getMaxTemperature())
                    .minTemperature(sensorAlert.getMinTemperature())
                    .build();
        }

        if (sensorAlert.getMinTemperature() != null
                && value.compareTo(sensorAlert.getMinTemperature()) <= 0) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.MIN_EXCEEDED)
                    .sensorId(temperatureLogData.getSensorId())
                    .currentTemperature(value)
                    .maxTemperature(sensorAlert.getMaxTemperature())
                    .minTemperature(sensorAlert.getMinTemperature())
                    .build();
        }

        return AlertEvaluationResult.builder()
                .status(AlertStatus.IGNORED)
                .sensorId(temperatureLogData.getSensorId())
                .currentTemperature(value)
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}
