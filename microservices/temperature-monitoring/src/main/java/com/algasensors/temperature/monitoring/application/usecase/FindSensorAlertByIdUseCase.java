package com.algasensors.temperature.monitoring.application.usecase;

import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface FindSensorAlertByIdUseCase {
    SensorAlert execute(SensorId sensorId);
}
