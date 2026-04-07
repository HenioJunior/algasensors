package com.algasensors.temperature.monitoring.application.usecase;

import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface FindSensorMonitoringByIdUseCase {
    SensorMonitoring execute(SensorId sensorId);
}
