package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.DeleteSensorUseCase;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSensorUseCaseImpl implements DeleteSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorIdParser sensorIdParser;

    @Override
    public void execute(Command command) {
        SensorId sensorId = sensorIdParser.parse(command.sensorId());

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(command.sensorId()));

        sensorGateway.delete(sensor);
    }
}
