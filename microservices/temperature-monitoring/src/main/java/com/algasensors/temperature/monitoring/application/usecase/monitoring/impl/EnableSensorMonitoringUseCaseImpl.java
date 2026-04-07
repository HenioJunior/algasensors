package com.algasensors.temperature.monitoring.application.usecase.monitoring.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.EnableSensorMonitoringUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnableSensorMonitoringUseCaseImpl implements EnableSensorMonitoringUseCase {

    private final SensorMonitoringGateway sensorMonitoringGateway;


    @Override
    @Transactional
    public void execute(SensorId sensorId) {
        SensorMonitoring sensorMonitoring = sensorMonitoringGateway.findById(sensorId)
                .orElseThrow(() -> new SensorMonitoringNotFoundException(sensorId));;
        SensorMonitoring.enable(sensorMonitoring);
        sensorMonitoringGateway.save(sensorMonitoring);
    }
}
