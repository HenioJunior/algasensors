package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.DisableSensorUseCase;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisableSensorUseCaseImpl implements DisableSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorMonitoringClient sensorMonitoringClient;
    private final SensorIdParser sensorIdParser;

    @Override
    public void execute(Command command) {
        SensorId sensorId = sensorIdParser.parse(command.sensorId());

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(command.sensorId()));

        sensor.disable();
        sensorGateway.save(sensor);

        TSID tsid = sensorId.getValue();
        sensorMonitoringClient.disableMonitoring(tsid);
    }
}
