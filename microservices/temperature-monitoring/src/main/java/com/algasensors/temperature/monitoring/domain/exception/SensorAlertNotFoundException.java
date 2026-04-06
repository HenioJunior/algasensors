package com.algasensors.temperature.monitoring.domain.exception;

import com.algasensors.temperature.monitoring.domain.model.SensorId;

public class SensorAlertNotFoundException extends RuntimeException {
    public SensorAlertNotFoundException(SensorId sensorId) {
        super("Sensor alert not found with id: " + sensorId);
    }
}
