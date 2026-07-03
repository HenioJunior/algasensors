package com.algasensors.device.management.application.usecase;

import com.algasensors.device.management.api.response.SensorMonitoringResponse;
import com.algasensors.device.management.domain.model.Sensor;

public interface FindSensorDetailUseCase {

    Result execute(Query query);

    record Query(String sensorId) {
    }

    record Result(
            Sensor sensor,
            SensorMonitoringResponse monitoring
    ) {
    }
}