package com.algasensors.device.management.api.exception;

import com.algasensors.device.management.domain.exception.SensorMonitoringClientBadGatewayException;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SensorNotFoundException.class)
    public ProblemDetail handleSensorNotFound(SensorNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Sensor not found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(InvalidSensorIdException.class)
    public ProblemDetail handleInvalidSensorId(InvalidSensorIdException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid sensor id");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    public ProblemDetail handle(IOException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setTitle("Gateway Timeout");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(SensorMonitoringClientBadGatewayException.class)
    public ProblemDetail handle(SensorMonitoringClientBadGatewayException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setTitle("Bad Gateway");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;

    }

}
