package com.algasensors.temperature.monitoring.application.usecase.alert.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.DeleteSensorAlertUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSensorAlertUseCaseImpl implements DeleteSensorAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;


    @Override
    @Transactional
    public void execute(SensorId sensorId) {
        SensorAlert sensorAlert = sensorAlertGateway.findById(sensorId)
                .orElseThrow(() -> new SensorAlertNotFoundException(sensorId));
        sensorAlertGateway.delete(sensorAlert);
    }
}
