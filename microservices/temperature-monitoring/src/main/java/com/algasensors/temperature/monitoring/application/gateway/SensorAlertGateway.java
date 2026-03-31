package com.algasensors.temperature.monitoring.application.gateway;

import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;

import java.util.Optional;

public interface SensorAlertGateway {

    Optional<SensorAlert> findBySensorId(SensorId sensorId);
}
