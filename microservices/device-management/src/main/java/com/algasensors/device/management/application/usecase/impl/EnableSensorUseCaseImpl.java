package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.application.usecase.EnableSensorUseCase;
import com.algasensors.device.management.domain.exception.SensorEnabledException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorClient;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnableSensorUseCaseImpl implements EnableSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorMonitoringClient sensorMonitoringClient;
    private final SensorClient sensorClient;
    private final SensorIdParser sensorIdParser;

    @Override
    @Transactional
    public void execute(Command command) {
        SensorId sensorId = sensorIdParser.parse(command.sensorId());

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(command.sensorId()));

        if (Boolean.TRUE.equals(sensor.getEnabled())) {
            log.warn("Temperature is already enabled for sensor {}", sensor.getId());
            throw new SensorEnabledException(sensor.getId());
        }

        sensor.enable();
        sensorGateway.save(sensor);

        sensorMonitoringClient.enableMonitoring(sensorId);
        sensorClient.startTransmission(sensorId);
    }
}
