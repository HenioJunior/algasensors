package com.algasensors.temperature.monitoring.application.usecase.temperature.impl;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.ValidateSensorMonitoringExistsUseCase;
import com.algasensors.temperature.monitoring.application.usecase.temperature.CreateTemperatureLogUseCase;
import com.algasensors.temperature.monitoring.application.usecase.temperature.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.monitoring.application.usecase.temperature.UpdateSensorMonitoringFromReadingUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.service.ProcessTemperatureAlertUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessTemperatureReadingUseCaseImpl implements ProcessTemperatureReadingUseCase {

    private final ValidateSensorMonitoringExistsUseCase validateSensorMonitoringExistsUseCase;
    private final UpdateSensorMonitoringFromReadingUseCase updateSensorMonitoringFromReadingUseCase;
    private final CreateTemperatureLogUseCase createTemperatureLogUseCase;
    private final ProcessTemperatureAlertUseCase processTemperatureAlertUseCase;

    @Override
    @Transactional
    public void execute(TemperatureLogResponse temperatureLogResponse) {
        SensorMonitoring sensorMonitoring =
                validateSensorMonitoringExistsUseCase.execute(temperatureLogResponse.getSensorId());

        if (!sensorMonitoring.isEnabled()) {
            log.warn("Ignored temperature reading for disabled sensor {}: {}",
                    temperatureLogResponse.getSensorId(),
                    temperatureLogResponse.getValue());
            return;
        }

        updateSensorMonitoringFromReadingUseCase.execute(sensorMonitoring, temperatureLogResponse);
        createTemperatureLogUseCase.execute(temperatureLogResponse);
        processTemperatureAlertUseCase.execute(temperatureLogResponse);
    }
}
