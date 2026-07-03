package com.algasensors.temperature.monitoring.application.gateway;

import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemperatureLogGateway {
    TemperatureLog save(TemperatureLog temperatureLog);
    Page<TemperatureLog> findAllBySensorId(SensorId sensorId, Pageable pageable);
}
