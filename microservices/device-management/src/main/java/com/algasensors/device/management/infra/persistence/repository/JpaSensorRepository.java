package com.algasensors.device.management.infra.persistence.repository;

import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSensorRepository extends JpaRepository<Sensor, SensorId>{}