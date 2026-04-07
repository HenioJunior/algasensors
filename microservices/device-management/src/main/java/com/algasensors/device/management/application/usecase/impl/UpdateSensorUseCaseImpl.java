package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.UpdateSensorUseCase;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSensorUseCaseImpl implements UpdateSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorIdParser sensorIdParser;

    @Override
    @Transactional
    public Sensor execute(Command command) {
        SensorId sensorId = sensorIdParser.parse(command.sensorId());

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(command.sensorId()));

        sensor.update(
                command.name(),
                command.location(),
                command.ip(),
                command.protocol(),
                command.model()
        );
        return sensorGateway.save(sensor);
    }
}
