package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;

public interface UpdateSensorMonitoringFromReadingUseCase {
    void execute(SensorMonitoring sensorMonitoring, TemperatureLogResponse temperatureLogResponse);
}
