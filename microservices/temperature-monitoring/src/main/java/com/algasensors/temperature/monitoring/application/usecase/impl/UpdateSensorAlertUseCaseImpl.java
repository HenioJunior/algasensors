package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.UpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSensorAlertUseCaseImpl implements UpdateSensorAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;

    @Override
    public void execute(SensorId sensorId, SensorAlertRequest request) {

        SensorAlert sensorAlert = sensorAlertGateway.findById(sensorId)
                .orElseThrow(() -> new SensorAlertNotFoundException(sensorId));

        sensorAlert.updateTemperatureRange(request.getMinTemperature(), request.getMaxTemperature());
    }
}
