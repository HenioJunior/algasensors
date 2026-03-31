package com.algasensors.device.management.application.support;

import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

@Component
public class SensorIdParser {

    public SensorId parse(String rawSensorId) {
        try {
            return new SensorId(TSID.from(rawSensorId));
        } catch (Exception ex) {
            throw new InvalidSensorIdException(rawSensorId);
        }
    }
}