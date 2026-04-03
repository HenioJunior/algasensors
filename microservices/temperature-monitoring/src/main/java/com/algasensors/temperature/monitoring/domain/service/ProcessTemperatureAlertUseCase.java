package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogData;
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

    public AlertEvaluationResult execute(TemperatureLogData temperatureLogData) {
        Optional<SensorAlert> sensorAlert =
                sensorAlertGateway.findBySensorId(temperatureLogData.getSensorId());

        return temperatureAlertEvaluator.evaluate(temperatureLogData, sensorAlert);
    }
}
