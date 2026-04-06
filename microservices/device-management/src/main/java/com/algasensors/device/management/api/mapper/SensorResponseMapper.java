package com.algasensors.device.management.api.mapper;

import com.algasensors.device.management.api.response.SensorResponse;
import com.algasensors.device.management.domain.model.Sensor;
import org.springframework.stereotype.Component;

@Component
public class SensorResponseMapper {

    public SensorResponse toResponse(Sensor sensor) {
        return SensorResponse.builder()
                .id(sensor.getId().getId().toString())
                .name(sensor.getName())
                .location(sensor.getLocation())
                .ip(sensor.getIp())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}
