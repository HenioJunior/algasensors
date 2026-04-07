package com.algasensors.temperature.monitoring.persistence.repository;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
}
