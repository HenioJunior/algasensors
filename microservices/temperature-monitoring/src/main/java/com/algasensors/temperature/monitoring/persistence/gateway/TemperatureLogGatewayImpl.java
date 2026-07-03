package com.algasensors.temperature.monitoring.persistence.gateway;

import com.algasensors.temperature.monitoring.application.gateway.TemperatureLogGateway;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import com.algasensors.temperature.monitoring.persistence.repository.TemperatureLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemperatureLogGatewayImpl implements TemperatureLogGateway {

    private final TemperatureLogRepository temperatureLogRepository;

    @Override
    public TemperatureLog save(TemperatureLog temperatureLog) {
        return temperatureLogRepository.save(temperatureLog);
    }

    @Override
    public Page<TemperatureLog> findAllBySensorId(SensorId sensorId, Pageable pageable) {
        return temperatureLogRepository.findAllBySensorId(sensorId, pageable);
    }
}
