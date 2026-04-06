package com.algasensors.temperature.monitoring.application.usecase;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.domain.model.SensorId;

public interface UpdateSensorAlertUseCase {
    void execute(SensorId sensorId, SensorAlertRequest request);
}
