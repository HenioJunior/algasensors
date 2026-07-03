package com.algasensors.temperature.monitoring.application.usecase.temperature.impl;

import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;
import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.temperature.UpdateSensorMonitoringFromReadingUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UpdateSensorMonitoringFromReadingUseCaseImpl implements UpdateSensorMonitoringFromReadingUseCase {

    private final SensorMonitoringGateway sensorMonitoringGateway;

    @Override
    @Transactional
    public void execute(SensorMonitoring sensorMonitoring, TemperatureReading temperatureReading) {
        sensorMonitoring.setLastTemperature(temperatureReading.getValue());
        sensorMonitoring.setUpdatedAt(OffsetDateTime.now());
        sensorMonitoringGateway.save(sensorMonitoring);
    }
}
