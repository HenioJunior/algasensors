package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorAlertByIdUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorAlertByIdUseCaseImpl implements FindSensorAlertByIdUseCase {

    private final SensorAlertGateway sensorAlertGateway;

    @Override
    public SensorAlert execute(SensorId sensorId) {
        return sensorAlertGateway.findById(sensorId)
                .orElseThrow(() -> new SensorAlertNotFoundException(sensorId));
    }
}
