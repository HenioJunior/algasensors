package com.algasensors.temperature.monitoring.api.mapper;

import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import org.springframework.stereotype.Component;

@Component
public class SensorAlertResponseMapper {

    public SensorAlertResponse toResponse(SensorAlert sensorAlert) {
        return SensorAlertResponse.builder()
                .sensorId(sensorAlert.getSensorId())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}
