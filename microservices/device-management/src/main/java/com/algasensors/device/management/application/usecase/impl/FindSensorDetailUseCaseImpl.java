package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.api.response.SensorMonitoringResponse;
import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.FindSensorDetailUseCase;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorDetailUseCaseImpl implements FindSensorDetailUseCase {

    private final SensorGateway sensorGateway;
    private final SensorMonitoringClient sensorMonitoringClient;

    @Override
    public Result execute(Query query) {
        final SensorId sensorId;

        try {
            sensorId = SensorId.of(query.sensorId());
        } catch (Exception ex) {
            throw new InvalidSensorIdException(query.sensorId());
        }

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(query.sensorId()));

        SensorMonitoringResponse monitoring = sensorMonitoringClient.getDetail(sensorId);

        return new Result(sensor, monitoring);
    }
}
