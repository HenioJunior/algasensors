package com.algasensors.device.management.domain.exception;

public class SensorMonitoringClientBadGatewayException extends RuntimeException {
    public SensorMonitoringClientBadGatewayException() {
        super("Communication with the sensor monitoring service failed.");
    }
}
