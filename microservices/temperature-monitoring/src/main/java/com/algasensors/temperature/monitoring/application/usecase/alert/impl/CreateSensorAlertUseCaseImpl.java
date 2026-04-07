package com.algasensors.temperature.monitoring.application.usecase.alert.impl;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.CreateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.ValidateSensorMonitoringExistsUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorAlertUseCaseImpl implements CreateSensorAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;
    private final ValidateSensorMonitoringExistsUseCase validateSensorMonitoringExistsUseCase;

    @Override
    @Transactional
    public SensorAlert execute(SensorId sensorId, SensorAlertRequest request) {
        validateSensorMonitoringExistsUseCase.execute(sensorId);
        SensorAlert sensorAlert = SensorAlert.execute(sensorId, request);
        return sensorAlertGateway.save(sensorAlert);
    }
}
