package com.algasensors.device.management.domain.exception;

public class SensorMonitoringClientBadGatewayException extends RuntimeException {

    private final int status;
    private final String responseBody;

    public SensorMonitoringClientBadGatewayException(int status, String responseBody) {
        super("Communication with the sensor monitoring service failed. status=%d, body=%s"
                .formatted(status, responseBody));
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
