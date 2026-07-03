package com.algasensors.temperature.monitoring.application.usecase.alert.impl;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.FindSensorMonitoringByIdUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateOrUpdateSensorAlertUseCase implements com.algasensors.temperature.monitoring.application.usecase.alert.CreateOrUpdateSensorAlertUseCase {

    private final SensorAlertGateway sensorAlertGateway;
    private final FindSensorMonitoringByIdUseCase findSensorMonitoringByIdUseCase;

    @Override
    @Transactional
    public SensorAlert execute(SensorId sensorId, SensorAlertRequest request) {
        findSensorMonitoringByIdUseCase.execute(sensorId);
        SensorAlert sensorAlert = sensorAlertGateway.findBySensorId(sensorId)
                .map(existing -> existing.update(
                        request.getMinTemperature(),
                        request.getWarningMinTemperature(),
                        request.getWarningMaxTemperature(),
                        request.getMaxTemperature()
                ))
                .orElseGet(() -> SensorAlert.create(
                        sensorId,
                        request.getMinTemperature(),
                        request.getWarningMinTemperature(),
                        request.getWarningMaxTemperature(),
                        request.getMaxTemperature()
                ));

        return sensorAlertGateway.save(sensorAlert);
    }
}
