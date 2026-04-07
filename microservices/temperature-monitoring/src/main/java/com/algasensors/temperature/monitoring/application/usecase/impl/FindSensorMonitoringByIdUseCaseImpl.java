package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorMonitoringByIdUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorMonitoringByIdUseCaseImpl implements FindSensorMonitoringByIdUseCase {

    private final SensorMonitoringGateway sensorMonitoringGateway;

    @Override
    public SensorMonitoring execute(SensorId sensorId) {
        return sensorMonitoringGateway.findById(sensorId)
                .orElseThrow(() -> new SensorMonitoringNotFoundException(sensorId));
    }
}
