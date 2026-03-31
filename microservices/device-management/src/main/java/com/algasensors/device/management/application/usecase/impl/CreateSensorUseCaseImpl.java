package com.algasensors.device.management.application.usecase.impl;


import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.common.IdGenerator;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSensorUseCaseImpl implements CreateSensorUseCase {

    private final SensorGateway sensorGateway;

    @Override
    public Sensor execute(CreateSensorCommand command) {
        Sensor sensor = Sensor.create(
                new SensorId(IdGenerator.generateTSID()),
                command.name(),
                command.location(),
                command.ip(),
                command.protocol(),
                command.model()
        );
        return sensorGateway.save(sensor);
    }
}
