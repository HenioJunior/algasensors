package com.algasensors.temperature.monitoring.application.usecase.monitoring;

import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface CreateMonitoringUseCase {
    SensorMonitoring execute(SensorId sensorId);
}
