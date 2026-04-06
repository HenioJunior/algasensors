package com.algasensors.temperature.monitoring.infra.gateway;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorAlertGatewayImpl implements SensorAlertGateway {

    private final SensorAlertRepository sensorAlertRepository;

    @Override
    public SensorAlert save(SensorAlert sensorAlert) {
        return sensorAlertRepository.save(sensorAlert);
    }

    @Override
    public Page<SensorAlert> findAll(Pageable pageable) {
        return sensorAlertRepository.findAll(pageable);
    }

    @Override
    public void delete(SensorAlert sensorAlert) {
        sensorAlertRepository.delete(sensorAlert);
    }

    @Override
    public Optional<SensorAlert> findById(SensorId sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId.getValue()));
    }
}
