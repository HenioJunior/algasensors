package com.algasensors.device.management.domain.exception;

import com.algasensors.device.management.domain.valueobject.SensorId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SensorEnabledException extends RuntimeException{
    public SensorEnabledException(SensorId sensorId) {
        super("Temperature is already enabled: " + sensorId);
    }
}
