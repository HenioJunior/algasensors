package com.algasensors.temperature.monitoring.api.mapper;

import com.algasensors.temperature.monitoring.api.response.SensorMonitoringResponse;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import org.springframework.stereotype.Component;

@Component
public class SensorMonitoringResponseMapper {
    public SensorMonitoringResponse toResponse(SensorMonitoring sensorMonitoring) {
        return SensorMonitoringResponse.builder()
                .id(sensorMonitoring.getId())
                .enabled(sensorMonitoring.isEnabled())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updatedAt(sensorMonitoring.getUpdatedAt())
                .build();
    }
}
