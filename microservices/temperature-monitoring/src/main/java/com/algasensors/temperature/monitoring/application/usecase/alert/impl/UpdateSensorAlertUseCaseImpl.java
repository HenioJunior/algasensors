package com.algasensors.temperature.monitoring.application.usecase.alert.impl;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.UpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSensorAlertUseCaseImpl implements UpdateSensorAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;

    @Override
    @Transactional
    public void execute(SensorId sensorId, SensorAlertRequest request) {

        SensorAlert sensorAlert = sensorAlertGateway.findById(sensorId)
                .orElseThrow(() -> new SensorAlertNotFoundException(sensorId));

        sensorAlert.updateTemperatureRange(request.getMinTemperature(), request.getMaxTemperature());
    }
}
