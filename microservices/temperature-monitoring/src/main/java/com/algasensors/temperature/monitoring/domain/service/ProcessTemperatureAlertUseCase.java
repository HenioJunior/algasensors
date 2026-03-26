package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algasensors.temperature.monitoring.application.port.out.LoadSensorAlertPort;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessTemperatureAlertUseCase {

    private final LoadSensorAlertPort loadSensorAlertPort;
    private final TemperatureAlertEvaluator temperatureAlertEvaluator;

    public AlertEvaluationResult execute(TemperatureLogData temperatureLogData) {
        Optional<SensorAlert> sensorAlert =
                loadSensorAlertPort.findBySensorId(temperatureLogData.getSensorId());

        return temperatureAlertEvaluator.evaluate(temperatureLogData, sensorAlert);
    }
}
