package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.domain.model.SensorId;

public interface DeleteSensorAlertUseCase {
    void execute(SensorId sensorId);
}
