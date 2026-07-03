package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;

public interface UpdateSensorMonitoringFromReadingUseCase {
    void execute(SensorMonitoring sensorMonitoring, TemperatureReading temperatureReading);
}
