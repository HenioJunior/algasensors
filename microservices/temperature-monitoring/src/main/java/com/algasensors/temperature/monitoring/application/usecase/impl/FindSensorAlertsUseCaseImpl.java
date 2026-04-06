package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorAlertsUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorAlertsUseCaseImpl implements FindSensorAlertsUseCase {

    private final SensorAlertGateway sensorAlertGateway;


    @Override
    public Page<SensorAlert> execute(Pageable pageable) {
        return sensorAlertGateway.findAll(pageable);
    }
}
