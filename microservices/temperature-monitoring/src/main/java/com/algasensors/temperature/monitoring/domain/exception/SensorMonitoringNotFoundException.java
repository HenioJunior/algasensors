package com.algasensors.temperature.monitoring.domain.exception;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public class SensorMonitoringNotFoundException extends RuntimeException{
    public SensorMonitoringNotFoundException(SensorId sensorId) {
        super("Sensor monitoring not found with id: " + sensorId);
    }
}
