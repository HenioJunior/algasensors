package com.algasensors.device.management.domain.exception;

public class InvalidSensorIdException extends RuntimeException {

    public InvalidSensorIdException(String sensorId) {
        super("Invalid sensor id: " + sensorId);
    }
}
