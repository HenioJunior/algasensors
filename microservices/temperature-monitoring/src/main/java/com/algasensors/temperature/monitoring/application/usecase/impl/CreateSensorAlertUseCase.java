package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorAlertUseCase implements com.algasensors.temperature.monitoring.application.usecase.CreateSensorAlertUseCase {

    private final SensorAlertMapper sensorAlertMapper;
    private final SensorAlertGateway sensorAlertGateway;

    @Override
    public SensorAlert execute(SensorAlertRequest request) {
        SensorAlert sensorAlert = sensorAlertMapper.getSensorAlert(request);
        return sensorAlertGateway.save(sensorAlert);
    }
}
