package com.algasensors.device.management.infra.persistence.repository;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorGatewayImpl implements SensorGateway {

    private final SensorRepository sensorRepository;

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.saveAndFlush(sensor);
    }
}
