package com.algasensors.temperature.monitoring.api.controller.handler;

import com.algasensors.temperature.monitoring.domain.exception.SensorDisabledException;
import com.algasensors.temperature.monitoring.domain.exception.SensorEnabledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SensorEnabledException.class)
    public ProblemDetail isSensorEnabled(SensorEnabledException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Sensor is enabled");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(SensorDisabledException.class)
    public ProblemDetail isSensorEnabled(SensorDisabledException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Sensor is disabled");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}
