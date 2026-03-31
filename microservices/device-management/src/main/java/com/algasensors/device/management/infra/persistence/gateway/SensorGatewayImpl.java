package com.algasensors.device.management.infra.persistence.gateway;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.infra.persistence.repository.JpaSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorGatewayImpl implements SensorGateway {

    private final JpaSensorRepository jpaSensorRepository;

    @Override
    public Sensor save(Sensor sensor) {
        return jpaSensorRepository.saveAndFlush(sensor);
    }

    @Override
    public Optional<Sensor> findById(SensorId sensorId) {
        return jpaSensorRepository.findById(sensorId);
    }

    @Override
    public Page<Sensor> findAll(Pageable pageable) {
        return jpaSensorRepository.findAll(pageable);
    }

    @Override
    public void delete(Sensor sensor) {
        jpaSensorRepository.delete(sensor);
    }
}
