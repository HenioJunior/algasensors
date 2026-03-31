package com.algasensors.temperature.monitoring.infra.gateway;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorAlertGatewayImpl implements SensorAlertGateway {

    private final SensorAlertRepository sensorAlertRepository;

    @Override
    public Optional<SensorAlert> findBySensorId(SensorId sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId.getValue()));
    }
}
