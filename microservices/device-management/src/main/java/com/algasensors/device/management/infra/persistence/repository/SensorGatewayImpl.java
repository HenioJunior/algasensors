package com.algasensors.device.management.infra.persistence.repository;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorGatewayImpl implements SensorGateway {

    private final SensorRepository sensorRepository;

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.saveAndFlush(sensor);
    }

    @Override
    public Optional<Sensor> findById(SensorId sensorId) {
        return sensorRepository.findById(sensorId);
    }

    @Override
    public Page<Sensor> findAll(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }
}
