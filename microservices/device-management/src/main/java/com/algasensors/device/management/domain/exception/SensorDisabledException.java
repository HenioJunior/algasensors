package com.algasensors.device.management.domain.exception;

import com.algasensors.device.management.domain.valueobject.SensorId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SensorDisabledException extends RuntimeException{
    public SensorDisabledException(SensorId sensorId) {
        super("Temperature is already disabled: " + sensorId);
    }

}
