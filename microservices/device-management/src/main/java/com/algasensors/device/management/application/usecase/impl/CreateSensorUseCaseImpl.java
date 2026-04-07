package com.algasensors.device.management.application.usecase.impl;


import com.algasensors.device.management.application.gateway.MonitoringGateway;
import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorUseCaseImpl implements CreateSensorUseCase {

    private final SensorGateway sensorGateway;
    private final MonitoringGateway monitoringGateway;

    @Override
    @Transactional
    public Sensor execute(CreateSensorCommand command) {
        Sensor sensor = Sensor.create(
                SensorId.generate(),
                command.name(),
                command.location(),
                command.ip(),
                command.protocol(),
                command.model()
        );
        return sensorGateway.save(sensor);
    }
}
