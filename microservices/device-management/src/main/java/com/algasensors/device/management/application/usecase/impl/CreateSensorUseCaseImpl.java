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
        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(command.name())
                .location(command.location())
                .ip(command.ip())
                .protocol(command.protocol())
                .model(command.model())
                .enabled(Boolean.FALSE)
                .build();

        return sensorGateway.save(sensor);
    }
}
