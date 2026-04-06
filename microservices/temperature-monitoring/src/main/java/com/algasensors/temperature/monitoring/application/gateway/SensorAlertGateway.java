package com.algasensors.temperature.monitoring.application.gateway;

import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SensorAlertGateway {

    SensorAlert save(SensorAlert sensorAlert);

    Page<SensorAlert> findAll(Pageable pageable);

    void delete(SensorAlert sensorAlert);

    Optional<SensorAlert> findById(SensorId sensorId);



}
