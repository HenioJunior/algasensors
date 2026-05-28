package com.algasensors.temperature.monitoring.domain.exception;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SensorDisabledException extends RuntimeException{
    public SensorDisabledException(SensorId sensorId) {
        super("Temperature is already disabled: " + sensorId);
    }
}
