package com.algasensors.device.management.application.gateway;

import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SensorGateway {

    Sensor save(Sensor sensor);

    Optional<Sensor> findById(SensorId sensorId);

    Page<Sensor> findAll(Pageable pageable);

    void delete(Sensor sensor);
}
