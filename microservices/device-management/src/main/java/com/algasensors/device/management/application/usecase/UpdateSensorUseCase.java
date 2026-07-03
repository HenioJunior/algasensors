package com.algasensors.device.management.application.usecase;

import com.algasensors.device.management.domain.model.Sensor;

public interface UpdateSensorUseCase {

    Sensor execute(Command command);

    record Command(
            String sensorId,
            String name,
            String location,
            String ip,
            String protocol,
            String model
    ) {
    }
}
