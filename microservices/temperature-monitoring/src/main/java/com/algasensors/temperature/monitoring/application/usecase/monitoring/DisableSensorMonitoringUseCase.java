package com.algasensors.temperature.monitoring.application.usecase.monitoring;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface DisableSensorMonitoringUseCase {
    void execute(SensorId sensorId);
}
