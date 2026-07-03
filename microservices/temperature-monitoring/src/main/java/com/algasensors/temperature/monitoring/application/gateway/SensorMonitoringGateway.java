package com.algasensors.temperature.monitoring.application.gateway;

import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SensorMonitoringGateway {

    SensorMonitoring save(SensorMonitoring sensorMonitoring);

    Page<SensorMonitoring> findAll(Pageable pageable);

    void delete(SensorMonitoring sensorMonitoring);

    Optional<SensorMonitoring> findById(SensorId sensorId);
}
