package com.algasensors.device.management.application.usecase.impl;


import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorUseCaseImpl implements CreateSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorMonitoringClient sensorMonitoringClient;

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
        sensorGateway.save(sensor);
        sensorMonitoringClient.create(sensor.getId());

        return sensor;
    }
}
