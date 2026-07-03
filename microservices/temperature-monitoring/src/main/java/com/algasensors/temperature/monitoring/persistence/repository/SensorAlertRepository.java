package com.algasensors.temperature.monitoring.persistence.repository;

import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {
    Optional<SensorAlert> findBySensorId(SensorId sensorId);
}
