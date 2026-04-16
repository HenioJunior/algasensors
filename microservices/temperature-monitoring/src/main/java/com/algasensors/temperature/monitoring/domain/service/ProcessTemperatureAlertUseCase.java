package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
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

    public AlertEvaluationResult execute(TemperatureLogResponse temperatureLogResponse) {
        Optional<SensorAlert> sensorAlert =
                sensorAlertGateway.findById(temperatureLogResponse.getSensorId());

        return temperatureAlertEvaluator.evaluate(temperatureLogResponse, sensorAlert);
    }
}
