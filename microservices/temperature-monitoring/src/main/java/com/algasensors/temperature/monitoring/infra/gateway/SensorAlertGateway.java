package com.algasensors.temperature.monitoring.infra.gateway;

import com.algasensors.temperature.monitoring.application.port.out.LoadSensorAlertPort;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorAlertGateway implements LoadSensorAlertPort {

    private final SensorAlertRepository sensorAlertRepository;

    @Override
    public Optional<SensorAlert> findBySensorId(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId));
    }
}
