package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSensorAlertUseCaseImpl implements DeleteSensorAlertUseCase{

    private final SensorAlertGateway sensorAlertGateway;


    @Override
    public void execute(SensorId sensorId) {
        SensorAlert sensorAlert = sensorAlertGateway.findById(sensorId)
                .orElseThrow(() -> new SensorAlertNotFoundException(sensorId));
        sensorAlertGateway.delete(sensorAlert);
    }
}
