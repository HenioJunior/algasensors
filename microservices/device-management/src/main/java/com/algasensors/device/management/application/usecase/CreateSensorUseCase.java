package com.algasensors.device.management.application.usecase;

import com.algasensors.device.management.domain.model.Sensor;

public interface CreateSensorUseCase {

    Sensor execute(CreateSensorCommand command);

    record CreateSensorCommand(
            String name,
            String location,
            String ip,
            String protocol,
            String model
    ) {
    }
}