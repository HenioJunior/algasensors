package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessTemperatureAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;
    private final TemperatureAlertEvaluator temperatureAlertEvaluator;

    public AlertEvaluationResult execute(TemperatureReading temperatureReading) {
        Optional<SensorAlert> sensorAlert =
                sensorAlertGateway.findBySensorId(temperatureReading.getSensorId());

        return temperatureAlertEvaluator.evaluate(temperatureReading, sensorAlert);
    }
}
