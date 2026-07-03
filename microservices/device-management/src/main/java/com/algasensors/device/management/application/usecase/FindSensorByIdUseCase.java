package com.algasensors.device.management.application.usecase;

import com.algasensors.device.management.domain.model.Sensor;

public interface FindSensorByIdUseCase {
    Sensor execute(Query query);

    record Query(String sensorId) {
    }
}
