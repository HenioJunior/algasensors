package com.algasensors.temperature.monitoring.application.usecase.monitoring.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.CreateMonitoringUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CreateSensorMonitoringUseCaseImpl implements CreateMonitoringUseCase {

    private final SensorMonitoringGateway sensorMonitoringGateway;

    @Override
    public void execute(SensorId sensorId) {
        SensorMonitoring sensorMonitoring =
                SensorMonitoring.builder().id(sensorId).lastTemperature(BigDecimal.valueOf(0.0)).updatedAt(OffsetDateTime.now()).enabled(true).build();
        sensorMonitoringGateway.save(sensorMonitoring);
    }
}
