package com.algasensors.device.management.domain.exception;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String sensorId) {
        super("Sensor not found with id: " + sensorId);
    }
}
