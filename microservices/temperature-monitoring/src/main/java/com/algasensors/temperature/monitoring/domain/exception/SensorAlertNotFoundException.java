package com.algasensors.temperature.monitoring.domain.exception;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SensorAlertNotFoundException extends RuntimeException {
    public SensorAlertNotFoundException(SensorId sensorId) {
        super("Sensor alert not found with id: " + sensorId);
    }
}
