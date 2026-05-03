package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TemperatureAlertEvaluator {

    public AlertEvaluationResult evaluate(TemperatureReading temperatureReading,
                                          Optional<SensorAlert> sensorAlertOptional) {

        BigDecimal value = temperatureReading.getValue();

        if (sensorAlertOptional.isEmpty()) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.IGNORED)
                    .sensorId(temperatureReading.getSensorId())
                    .currentTemperature(value)
                    .build();
        }

        SensorAlert sensorAlert = sensorAlertOptional.get();

        if (sensorAlert.getMaxTemperature() != null
                && value.compareTo(sensorAlert.getMaxTemperature()) >= 0) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.MAX_EXCEEDED)
                    .sensorId(temperatureReading.getSensorId())
                    .currentTemperature(value)
                    .maxTemperature(sensorAlert.getMaxTemperature())
                    .minTemperature(sensorAlert.getMinTemperature())
                    .build();
        }

        if (sensorAlert.getMinTemperature() != null
                && value.compareTo(sensorAlert.getMinTemperature()) <= 0) {
            return AlertEvaluationResult.builder()
                    .status(AlertStatus.MIN_EXCEEDED)
                    .sensorId(temperatureReading.getSensorId())
                    .currentTemperature(value)
                    .maxTemperature(sensorAlert.getMaxTemperature())
                    .minTemperature(sensorAlert.getMinTemperature())
                    .build();
        }

        return AlertEvaluationResult.builder()
                .status(AlertStatus.IGNORED)
                .sensorId(temperatureReading.getSensorId())
                .currentTemperature(value)
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}
