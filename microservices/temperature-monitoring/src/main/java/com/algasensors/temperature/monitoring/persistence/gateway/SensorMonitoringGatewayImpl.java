package com.algasensors.temperature.monitoring.persistence.gateway;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.persistence.repository.SensorMonitoringRepository;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorMonitoringGatewayImpl implements SensorMonitoringGateway {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    @Override
    public SensorMonitoring save(SensorMonitoring sensorMonitoring) {
        return sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @Override
    public Page<SensorMonitoring> findAll(Pageable pageable) {
        return sensorMonitoringRepository.findAll(pageable);
    }

    @Override
    public void delete(SensorMonitoring sensorMonitoring) {
        sensorMonitoringRepository.delete(sensorMonitoring);
    }

    @Override
    public Optional<SensorMonitoring> findById(SensorId sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId.getValue()));
    }
}
