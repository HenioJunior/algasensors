package com.algasensors.temperature.monitoring.application.usecase.alert;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface DeleteSensorAlertUseCase {
    void execute(SensorId sensorId);
}
