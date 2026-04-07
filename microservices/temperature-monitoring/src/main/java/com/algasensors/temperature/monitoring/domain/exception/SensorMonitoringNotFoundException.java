package com.algasensors.temperature.monitoring.domain.exception;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SensorMonitoringNotFoundException extends RuntimeException{
    public SensorMonitoringNotFoundException(SensorId sensorId) {
        super("Sensor monitoring not found with id: " + sensorId);
    }
}
