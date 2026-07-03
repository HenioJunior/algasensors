package com.algasensors.temperature.monitoring.application.usecase.monitoring.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.CreateMonitoringUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorMonitoringUseCaseImpl implements CreateMonitoringUseCase {

    private final SensorMonitoringGateway sensorMonitoringGateway;

    @Override
    public SensorMonitoring execute(SensorId sensorId) {
        SensorMonitoring sensorMonitoring = SensorMonitoring.create(sensorId);
        sensorMonitoringGateway.save(sensorMonitoring);
        return sensorMonitoring;
    }
}
