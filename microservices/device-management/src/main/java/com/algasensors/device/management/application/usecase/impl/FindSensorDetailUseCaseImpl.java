package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algasensors.device.management.api.response.SensorMonitoringOutput;
import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.FindSensorDetailUseCase;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
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
        final TSID tsid;

        try {
            tsid = TSID.from(query.sensorId());
        } catch (Exception ex) {
            throw new InvalidSensorIdException(query.sensorId());
        }

        Sensor sensor = sensorGateway.findById(new SensorId(tsid))
                .orElseThrow(() -> new SensorNotFoundException(query.sensorId()));

        SensorMonitoringOutput monitoring = sensorMonitoringClient.getDetail(tsid);

        return new Result(sensor, monitoring);
    }

}
