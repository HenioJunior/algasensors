package com.algasensors.device.management.domain.exception;

import com.algasensors.device.management.domain.valueobject.SensorId;

public class SensorMonitoringNotFoundException extends RuntimeException {
    public SensorMonitoringNotFoundException(SensorId sensorId) {
        super("Sensor monitoring not found with id: " + sensorId.getValue());
    }
}
